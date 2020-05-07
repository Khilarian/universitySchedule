package com.rumakin.universityschedule.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.enums.TimeSlot;

@Repository
public class TimeSlotDao extends Dao<TimeSlot> {

    @Autowired
    public TimeSlotDao() {
    }

    @Override
    protected String getModelClassName() {
        return TimeSlot.class.getSimpleName();
    }

    @Override
    protected Class<TimeSlot> getEntityClass() {
        return TimeSlot.class;
    }

}
