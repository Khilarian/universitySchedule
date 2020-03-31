package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.enums.TimeSlot;

@Repository
public class TimeSlotDao implements Dao<TimeSlot> {

    private static final String TABLE = "time_slot";
    private static final String NAME = "time_slot_name";

    private static final String ADD = "INSERT INTO " + TABLE + " (" + NAME + ") values (?);";
    private static final String FIND_BY_NAME = "SELECT * FROM " + TABLE + " WHERE " + NAME + "=?;";
    private static final String FIND_ALL = "SELECT " + NAME + " FROM " + TABLE + ";";
    private static final String REMOVE_BY_NAME = "DELETE FROM " + TABLE + " WHERE " + NAME + " =?;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TimeSlotDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TimeSlot> addAll(List<TimeSlot> timeSlots) {
        this.jdbcTemplate.batchUpdate(ADD, new BatchComposer<TimeSlot>(timeSlots, this));
        return timeSlots;
    }

    @Override
    public TimeSlot add(TimeSlot timeSlot) {
        String timeSlotName = timeSlot.name();
        this.jdbcTemplate.update(ADD, timeSlotName);
        return timeSlot;
    }

    @SuppressWarnings("hiding")
    @Override
    public <String> TimeSlot find(String name) {
        return this.jdbcTemplate.queryForObject(FIND_BY_NAME, new Object[] { name }, mapRow());
    }

    @Override
    public List<TimeSlot> findAll() {
        return this.jdbcTemplate.query(FIND_ALL, mapRow());
    }

    @SuppressWarnings("hiding")
    @Override
    public <String> void remove(String name) {
        this.jdbcTemplate.update(REMOVE_BY_NAME, name);
    }

    @Override
    public void setParameters(PreparedStatement ps, TimeSlot timeSlot) throws SQLException {
        String timeSlotName = timeSlot.name();
        ps.setString(1, timeSlotName);
    }

    @Override
    public RowMapper<TimeSlot> mapRow() {
        return (ResultSet rs, int rowNumver) -> TimeSlot.valueOf(rs.getString(NAME));
    }

    @Override
    public java.lang.String getFieldsList(java.lang.String alias) {
        List<String> fields = Arrays.asList(NAME);
        return formatFieldsList(alias, fields);
    }

}