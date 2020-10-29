package com.rumakin.universityschedule.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.model.TimeSlot;

@Repository
public interface TimeSlotDao extends CrudRepository<TimeSlot, Integer> {
    
    public Optional<TimeSlot> findByNumber(int number);
    
}
