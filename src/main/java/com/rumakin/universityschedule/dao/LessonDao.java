package com.rumakin.universityschedule.dao;

import java.time.LocalDate;
import java.util.*;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.dao.custom.CustomizedLessonDao;
import com.rumakin.universityschedule.model.*;

@Repository
public interface LessonDao extends CrudRepository<Lesson, Integer>, CustomizedLessonDao {

    public Set<Integer> getAllByIdIsNotAndDateEqualsAndTimeSlot_IdEqualsAndGroupsIn(Integer id, LocalDate date, Integer timeSlotId, Set<Group> groups);
}
