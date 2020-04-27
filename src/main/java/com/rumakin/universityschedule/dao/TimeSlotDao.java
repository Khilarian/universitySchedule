package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.enums.TimeSlot;

@Repository
public class TimeSlotDao extends Dao<TimeSlot> {

    private static final String TABLE = "time_slot";
    private static final String ALIAS = "ts";
    private static final String ID = "time_slot_id";
    private static final String NAME = "time_slot_name";

    @Autowired
    public TimeSlotDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getTableName() {
        return TABLE;
    }

    @Override
    protected String getTableAlias() {
        return ALIAS;
    }

    @Override
    protected String getEntityIdName() {
        return ID;
    }

    @Override
    protected List<String> getFieldsNames() {
        return Arrays.asList(NAME);
    }

    @Override
    public RowMapper<TimeSlot> mapRow() {
        return (ResultSet rs, int rowNumver) -> TimeSlot.valueOf(rs.getString(NAME));
    }

    @Override
    protected Object[] getFieldValues(TimeSlot entity) {
        return new Object[] { NAME };
    }

    @Override
    protected String getModelClassName() {
        return TimeSlot.class.getSimpleName();
    }

}
