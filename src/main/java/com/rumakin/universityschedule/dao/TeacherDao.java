package com.rumakin.universityschedule.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.model.Teacher;

@Repository
public interface TeacherDao extends CrudRepository<Teacher, Integer> {
}
