package com.rumakin.universityschedule.dao;

import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.exceptions.InvalidEntityException;
import com.rumakin.universityschedule.models.Entity;

public abstract class Dao<T> {

    protected static final String ADD = "INSERT INTO %s %s (%s) VALUES (%s) RETURNING %s;";
    protected static final String FIND_ALL = "SELECT * FROM %s;";
    protected static final String FIND_BY_ID = "SELECT * FROM %s WHERE %s=?;";
    protected static final String REMOVE_BY_ID = "DELETE FROM %s WHERE %s=?;";

    protected JdbcTemplate jdbcTemplate;
    protected final Logger logger;

    @Autowired
    protected Dao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger = LoggerFactory.getLogger(this.getClass());
    }

    protected abstract String getTableName();

    protected abstract String getTableAlias();

    protected abstract String getEntityIdName();

    protected abstract List<String> getFieldsNames();

    protected abstract RowMapper<T> mapRow();

    protected abstract Object[] getFieldValues(T entity);

    protected abstract String getModelClassName();

    public T add(T entity) {
        logger.debug("add() {}", entity);
        Object[] input = getFieldValues(entity);
        String sql = String.format(ADD, getTableName(), getTableAlias(), formatFieldsList(),
                inputFieldPrepare(input.length), getEntityIdName());
        int id = jdbcTemplate.queryForObject(sql, input, Integer.class);
        ((Entity) entity).setId(id);
        logger.trace("add() was complete for  {}", entity);
        return entity;
    }

    public List<T> addAll(List<T> entities) {
        logger.debug("addAll() {}", entities);
        for (T entity : entities) {
            add(entity);
        }
        logger.trace("addAll() was complete");
        return entities;
    }

    public T find(int id) {
        logger.debug("find() '{}'", id);
        String sql = String.format(FIND_BY_ID, getTableName(), getEntityIdName());
        return jdbcTemplate.queryForObject(sql, new Object[] { id }, mapRow());
    }

    public List<T> findAll() {
        logger.debug("findAll()");
        String sql = String.format(FIND_ALL, getTableName());
        return jdbcTemplate.query(sql, mapRow());
    }

    public void remove(int id) {
        logger.debug("remove() '{}'", id);
        String sql = String.format(REMOVE_BY_ID, getTableName(), getEntityIdName());
        jdbcTemplate.update(sql, id);
    }

    protected String formatFieldsList() {
        return getFieldsNames().stream().map(a -> addAlias(getTableAlias(), a)).reduce((a, b) -> a + ',' + b)
                .orElseThrow(() -> new InvalidEntityException(getModelClassName()));
    }

    protected String addAlias(String alias, String text) {
        return alias + "." + text;
    }

    private static String inputFieldPrepare(int size) {
        StringBuilder builder = new StringBuilder("?");
        for (int i = 1; i < size; i++) {
            builder.append(",").append("?");
        }
        return builder.toString();
    }

}
