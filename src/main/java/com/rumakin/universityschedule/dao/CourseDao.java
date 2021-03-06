package com.rumakin.universityschedule.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.model.Course;

@Repository
public interface CourseDao extends CrudRepository<Course, Integer> {

    public Optional<Course> findByName(String name);
}
