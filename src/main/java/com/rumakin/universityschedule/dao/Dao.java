package com.rumakin.universityschedule.dao;

import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.exceptions.*;
import com.rumakin.universityschedule.models.Entity;

public abstract class Dao<T> {

    protected static final String ADD = "INSERT INTO %s (%s) VALUES (%s) RETURNING %s;";
    protected static final String FIND_ALL = "SELECT * FROM %s;";
    protected static final String FIND_BY_ID = "SELECT * FROM %s WHERE %s=?;";
    protected static final String DELETE_BY_ID = "DELETE FROM %s WHERE %s=?;";
    protected static final String UPDATE = "UPDATE %s SET %s WHERE %s=?";

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
        String sql = String.format(ADD, getTableName(), formatFieldsList(), inputFieldPrepare(input.length),
                getEntityIdName());
        int id = jdbcTemplate.queryForObject(sql, input, Integer.class);
        ((Entity) entity).setId(id);
        logger.trace("add() was complete for {}", entity);
        return entity;
    }

    public List<T> addAll(List<T> entities) {
        logger.debug("addAll() {}", entities);
        for (T entity : entities) {
            add(entity);
        }
        logger.trace("addAll() was complete for {} objects", entities.size());
        return entities;
    }

    public T find(int id) {
        logger.debug("find() '{}'", id);
        String sql = String.format(FIND_BY_ID, getTableName(), getEntityIdName());
        T result = jdbcTemplate.queryForObject(sql, new Object[] { id }, mapRow());
        if (result == null) {
            throw new DaoException(getModelClassName() + " with id " + id + "was not found.");
        }
        logger.trace("found {}", result);
        return result;
    }

    public List<T> findAll() {
        logger.debug("findAll()");
        String sql = String.format(FIND_ALL, getTableName());
        List<T> result = jdbcTemplate.query(sql, mapRow());
        logger.trace("findAll() found {} entries.", result.size());
        return result;
    }

    public boolean delete(int id) {
        logger.debug("delete() '{}'", id);
        String sql = String.format(DELETE_BY_ID, getTableName(), getEntityIdName());
        boolean result = jdbcTemplate.update(sql, id) == 1;
        if (result) {
            logger.trace("delete(): entry {} was removed.", id);
        } else {
            logger.trace("delete(): entry {} was not found.", id);
        }
        return result;
    }

    public void update(T entity) {
        logger.debug("update() {}", entity);
        String sql = String.format(UPDATE, getTableName(), prepareFieldsForUpdate(), getEntityIdName());
        jdbcTemplate.update(sql, ((Entity) entity).getId());
    }

    protected String addAlias(String alias, String text) {
        return alias + "." + text;
    }

    private String formatFieldsList() {
        return getFieldsNames().stream().reduce((a, b) -> a + ',' + b)
                .orElseThrow(() -> new InvalidEntityException(getModelClassName()));
    }

    private String prepareFieldsForUpdate() {
        return getFieldsNames().stream().map(a -> a + "=").reduce((a, b) -> a + ',' + b)
                .orElseThrow(() -> new InvalidEntityException(getModelClassName()));
    }

    private static String inputFieldPrepare(int size) {
        StringBuilder builder = new StringBuilder("?");
        for (int i = 1; i < size; i++) {
            builder.append(",").append("?");
        }
        return builder.toString();
    }

}
