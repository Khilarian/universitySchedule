package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.List;

import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.enums.TimeSlot;
import com.rumakin.universityschedule.exceptions.DaoException;

public class TimeSlotDao implements Dao<TimeSlot>, StatementFiller<TimeSlot> {
    private static final String TABLE_NAME = "time_slot";
    private static final String NAME = "time_slot_name";

    private static final String ADD = "INSERT INTO " + TABLE_NAME + " (" + NAME + ") values (?);";
    private static final String FIND_BY_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + "=?;";
    private static final String FIND_ALL = "SELECT " + NAME + " FROM " + TABLE_NAME + ";";

    public final JdbcTemplate jdbcTemplate;

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

    public TimeSlot findByName(String name) {
        TimeSlot timeSlot = this.jdbcTemplate.queryForObject(FIND_BY_NAME, new Object[] { name },
                mapRow());
        if (timeSlot == null) {
            throw new DaoException("TimeSlot with name " + name + " is absent.");
        }
        return timeSlot;
    }

    @Override
    public TimeSlot findById(int id) {
        return null;
    }

    @Override
    public List<TimeSlot> findAll() {
        List<TimeSlot> timeSlots = this.jdbcTemplate.query(FIND_ALL, mapRow());
        if (timeSlots.isEmpty()) {
            throw new DaoException("TimeSlot table is empty.");
        }
        return timeSlots;
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
