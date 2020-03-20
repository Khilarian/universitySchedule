package com.rumakin.universityschedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dao.TimeSlotDao;
import com.rumakin.universityschedule.enums.TimeSlot;

public class TimeSlotService {

private final TimeSlotDao timeSlotDao;
    
    @Autowired
    public TimeSlotService(TimeSlotDao timeSlotDao) {
        this.timeSlotDao = timeSlotDao;
    }
    
    public void add(TimeSlot timeSlot) {
        timeSlotDao.add(timeSlot);
    }
    
    public void addAll(List<TimeSlot> timeSlots) {
        timeSlotDao.addAll(timeSlots);
    }
    
    public TimeSlot find(String name) {
        return timeSlotDao.find(name);
    }
    
    public List<TimeSlot> findAll(){
        return timeSlotDao.findAll();
    }
    
    public void remove(int id) {
        timeSlotDao.remove(id);
    }
}
