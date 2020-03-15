package com.rumakin.universityschedule.dao.addbatch;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.rumakin.universityschedule.models.TimeSlot;

public class TimeSlotAddBatch implements BatchPreparedStatementSetter {

    private final List<TimeSlot> timeSlots;

    public TimeSlotAddBatch(final List<TimeSlot> data) {
        this.timeSlots = data;
    }

    public final void setValues(final PreparedStatement ps, final int i) throws SQLException {
        ps.setString(1, timeSlots.get(i).name());
    }

    @Override
    public int getBatchSize() {
        return timeSlots.size();
    }
}