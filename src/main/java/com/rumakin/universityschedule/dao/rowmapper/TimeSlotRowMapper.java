package com.rumakin.universityschedule.dao.rowmapper;

import java.sql.*;

import org.springframework.jdbc.core.RowMapper;

import com.rumakin.universityschedule.models.TimeSlot;

public class TimeSlotRowMapper implements RowMapper<TimeSlot> {

    private static final String NAME = "time_slot_name";

    public TimeSlot mapRow(ResultSet rs, int rowNum) throws SQLException {
        return TimeSlot.valueOf(rs.getString(NAME));
    }
}
