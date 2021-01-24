package com.rumakin.universityschedule.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.model.*;

@Repository
public interface LessonTypeDao extends CrudRepository<LessonType, Integer> {

    public Optional<LessonType> findByName(String name);
    
}
