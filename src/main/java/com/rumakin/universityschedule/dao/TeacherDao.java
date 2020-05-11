package com.rumakin.universityschedule.dao;

import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.Teacher;

@Repository
public interface TeacherDao extends Dao<Teacher, Integer> {
}
