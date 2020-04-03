package com.rumakin.universityschedule.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.rumakin.universityschedule.exceptions.InvalidEntityException;
import com.rumakin.universityschedule.models.Entity;

public abstract class Dao<T> {

    protected static final String ADD = "INSERT INTO %s %s (%s) VALUES (%s) RETURNING %s;";
    protected static final String FIND_ALL = "SELECT * FROM %s;";
    protected static final String FIND_BY_ID = "SELECT * FROM %s WHERE %s=?;";
    protected static final String REMOVE_BY_ID = "DELETE FROM %s WHERE %s=?;";

    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected Dao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    abstract String getTableName();

    abstract String getTableAlias();

    abstract String getEntityIdName();

    abstract List<String> getFieldsNames();

    abstract RowMapper<T> mapRow();

    abstract Object[] getFieldValues(T entity);

    public T add(T entity) {
        Object[] input = getFieldValues(entity);
        String sql = String.format(ADD, getTableName(), getTableAlias(), formatFieldsList(),
                inputFieldPrepare(input.length), getEntityIdName());
        int id = this.jdbcTemplate.queryForObject(sql, input, Integer.class);
        ((Entity) entity).setId(id);
        return entity;
    }

    public List<T> addAll(List<T> entities) {
        for (T entity : entities) {
            add(entity);
        }
        return entities;
    }

    public T find(int id) {
        String sql = String.format(FIND_BY_ID, getTableName(), getEntityIdName());
        return this.jdbcTemplate.queryForObject(sql, new Object[] { id }, mapRow());
    }

    public List<T> findAll() {
        String sql = String.format(FIND_ALL, getTableName());
        return this.jdbcTemplate.query(sql, mapRow());
    }

    public void remove(int id) {
        String sql = String.format(REMOVE_BY_ID, getTableName(), getEntityIdName());
        this.jdbcTemplate.update(sql, id);
    }

    protected String formatFieldsList() {
        return getFieldsNames().stream().map(a -> addAlias(getTableAlias(), a)).reduce((a, b) -> a + ',' + b)
                .orElseThrow(InvalidEntityException::new);
    }

    private static String inputFieldPrepare(int size) {
        StringBuilder builder = new StringBuilder("?");
        for (int i = 1; i < size; i++) {
            builder.append(",")
                    .append("?");
        }
        return builder.toString();
    }

    protected String addAlias(String alias, String text) {
        return alias + "." + text;
    }

}
