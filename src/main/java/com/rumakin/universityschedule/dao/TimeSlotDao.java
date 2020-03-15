package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.List;

import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.dao.addbatch.TimeSlotAddBatch;
import com.rumakin.universityschedule.models.TimeSlot;

public class TimeSlotDao implements Dao<TimeSlot> {
    private static final String TABLE_NAME = "time_slot";
    private static final String NAME = "time_slot_name";

    private static final String ADD_TIME_SLOT = "INSERT INTO " + TABLE_NAME + " (" + NAME + ") values (?);";
    private static final String FIND_BY_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + "=?;";
    private static final String FIND_ALL_TIME_SLOT = "SELECT " + NAME + " FROM " + TABLE_NAME + ";";

    public final JdbcTemplate jdbcTemplate;

    public TimeSlotDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addAll(List<TimeSlot> data) {
        this.jdbcTemplate.batchUpdate(ADD_TIME_SLOT, new TimeSlotAddBatch(data));
    }

    @Override
    public void add(TimeSlot entity) {
        String timeSlotName = entity.name();
        this.jdbcTemplate.update(ADD_TIME_SLOT, timeSlotName);
    }

    public TimeSlot findByName(String name) {
        return this.jdbcTemplate.queryForObject(FIND_BY_NAME,
                new Object[] { name },
                new RowMapper<TimeSlot>() {
                    public TimeSlot mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return TimeSlot.valueOf(rs.getString(NAME));
                    }
                });
    }

    @Override
    public TimeSlot findById(int id) {
        return null;
    }

    @Override
    public List<TimeSlot> findAll() {
        return this.jdbcTemplate.query(FIND_ALL_TIME_SLOT,
                new RowMapper<TimeSlot>() {
                    public TimeSlot mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return TimeSlot.valueOf(rs.getString(NAME));
                    }
                });
    }
}
