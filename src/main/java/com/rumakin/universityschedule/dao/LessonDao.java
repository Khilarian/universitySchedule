package com.rumakin.universityschedule.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.dao.custom.CustomizedLessonDao;
import com.rumakin.universityschedule.model.*;

@Repository
public interface LessonDao extends CrudRepository<Lesson, Integer>, CustomizedLessonDao {

}
