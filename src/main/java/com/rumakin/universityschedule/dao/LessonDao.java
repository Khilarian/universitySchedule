package com.rumakin.universityschedule.dao;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.model.*;

@Repository
public interface LessonDao extends CrudRepository<Lesson, Integer> {
    
//    public Lesson findByAuditoriumAndDateAndTimeSlotId(int auditoriumId, LocalDate date, int timeSlotId);
//    
//    public Lesson findByGroupAndDateAndTimeSlotId(int groupId, LocalDate date, int timeSlotId);
//    
//    public Lesson findByTeacherAndDateAndTimeSlotId(int teacherId, LocalDate date, int timeSlotId);
}

