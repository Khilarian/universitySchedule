package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.List;

import org.springframework.jdbc.core.*;
import com.rumakin.universityschedule.models.TimeSlot;

public class TimeSlotDao implements Dao<TimeSlot> {
    private static final String TABLE_NAME = "time_slot";
    private static final String ID = "time_slot_id";
    private static final String NAME = "time_slot_name";

    private static final String ADD_TIME_SLOT = "INSERT INTO " + TABLE_NAME
            + " (" + ID + "," + NAME + ") values (?,?);";
    private static final String FIND_TIME_SLOT_BY_ID = "SELECT " + NAME + " FROM "
            + TABLE_NAME + " WHERE " + ID + "=?;";
    private static final String FIND_ALL_TIME_SLOT = "SELECT " + NAME + " FROM "
            + TABLE_NAME + ";";

    public final JdbcTemplate jdbcTemplate;

    public TimeSlotDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class TimeSlotAddBatch implements BatchPreparedStatementSetter {

        private final List<TimeSlot> timeSlots;

        public TimeSlotAddBatch(final List<TimeSlot> data) {
            this.timeSlots = data;
        }

        public final void setValues(
                final PreparedStatement ps,
                final int i) throws SQLException {
            ps.setInt(1, timeSlots.get(i).getIndex());
            ps.setString(2, timeSlots.get(i).name());
        }

        @Override
        public int getBatchSize() {
            return timeSlots.size();
        }
    }

    @Override
    public void addAll(List<TimeSlot> data) {
        this.jdbcTemplate.batchUpdate(ADD_TIME_SLOT, new TimeSlotAddBatch(data));
    }

    @Override
    public void add(TimeSlot entity) {
        int timeSlotId = entity.getIndex();
        String timeSlotName = entity.name();
        this.jdbcTemplate.update(ADD_TIME_SLOT, timeSlotId, timeSlotName);
    }

    @Override
    public TimeSlot findById(int id) {
        return this.jdbcTemplate.queryForObject(FIND_TIME_SLOT_BY_ID,
                new Object[] { 1212L },
                new RowMapper<TimeSlot>() {
                    public TimeSlot mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return TimeSlot.valueOf(rs.getString(NAME));
                    }
                });
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
