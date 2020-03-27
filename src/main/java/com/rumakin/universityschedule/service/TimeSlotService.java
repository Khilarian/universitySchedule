package com.rumakin.universityschedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dao.TimeSlotDao;
import com.rumakin.universityschedule.models.enums.TimeSlot;

public class TimeSlotService {

private final TimeSlotDao timeSlotDao;
    
    @Autowired
    public TimeSlotService(TimeSlotDao timeSlotDao) {
        this.timeSlotDao = timeSlotDao;
    }
    
}
