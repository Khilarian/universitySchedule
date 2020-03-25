package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.rumakin.universityschedule.models.Group;

public class GroupDao implements Dao<Group> {

    private static final String TABLE = "group";
    private static final String ALIAS = "g";
    private static final String ID = "group_id";
    private static final String NAME = "group_name";

    private static final String ADD = "INSERT INTO " + TABLE + " (" + NAME + ") values (?);";
    private static final String FIND_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + ID + "=?;";
    private static final String FIND_ALL = "SELECT * FROM " + TABLE + ";";
    private static final String REMOVE_BY_ID = "DELETE FROM " + TABLE + " WHERE " + ID + " =?;";

    private static final String FIND_ALL_LESSON_BY_DATE = "SELECT ";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addAll(List<Group> data) {
        this.jdbcTemplate.batchUpdate(ADD, new BatchComposer<Group>(data, this));

    }

    @Override
    public void add(Group group) {
        String groupName = group.getName();
        this.jdbcTemplate.update(ADD, groupName);

    }

    @SuppressWarnings("hiding")
    @Override
    public <Integer> Group find(Integer id) {
        return this.jdbcTemplate.queryForObject(FIND_BY_ID, new Object[] { id }, mapRow());
    }

    @Override
    public List<Group> findAll() {
        return this.jdbcTemplate.query(FIND_ALL, mapRow());
    }

    @Override
    public RowMapper<Group> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Group(rs.getInt(ID), rs.getString(NAME));
    }

    @SuppressWarnings("hiding")
    @Override
    public <Integer> void remove(Integer id) {
        this.jdbcTemplate.update(REMOVE_BY_ID, id);
    }

    @Override
    public void setParameters(PreparedStatement ps, Group group) throws SQLException {
        String groupName = group.getName();
        ps.setString(1, groupName);
    }

    @Override
    public String getFieldsList() {
        return ALIAS + "." + ID + "," + ALIAS + "." + NAME;
    }

    @Override
    public String getTableName() {
        return TABLE;
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }

}
