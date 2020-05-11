package com.rumakin.universityschedule.dao;

import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.Course;

@Repository
public interface CourseDao extends Dao<Course, Integer> {
}