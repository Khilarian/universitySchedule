package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.enums.TimeSlot;
import com.rumakin.universityschedule.exceptions.DaoException;

public class TimeSlotDao implements Dao<TimeSlot>, ResultSetMapper<TimeSlot> {
    private static final String TABLE_NAME = "time_slot";
    private static final String NAME = "time_slot_name";

    private static final String ADD = "INSERT INTO " + TABLE_NAME + " (" + NAME + ") values (?);";
    private static final String FIND_BY_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + "=?;";
    private static final String FIND_ALL = "SELECT " + NAME + " FROM " + TABLE_NAME + ";";
    private static final String REMOVE_BY_NAME = "DELETE FROM " + TABLE_NAME + " WHERE " + NAME + " =?;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TimeSlotDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addAll(List<TimeSlot> data) {
        this.jdbcTemplate.batchUpdate(ADD, new BatchComposer<TimeSlot>(data, this));
    }

    @Override
    public void add(TimeSlot timeSlot) {
        String timeSlotName = timeSlot.name();
        this.jdbcTemplate.update(ADD, timeSlotName);
    }

    @SuppressWarnings("hiding")
    @Override
    public <String> TimeSlot find(String name) {
        TimeSlot timeSlot = this.jdbcTemplate.queryForObject(FIND_BY_NAME, new Object[] { name },
                mapRow());
        if (timeSlot == null) {
            throw new DaoException("TimeSlot with name " + name + " is absent.");
        }
        return timeSlot;
    }

    @Override
    public List<TimeSlot> findAll() {
        List<TimeSlot> timeSlots = this.jdbcTemplate.query(FIND_ALL, mapRow());
        if (timeSlots.isEmpty()) {
            throw new DaoException("TimeSlot table is empty.");
        }
        return timeSlots;
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
}
