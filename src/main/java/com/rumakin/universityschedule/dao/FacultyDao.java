package com.rumakin.universityschedule.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.model.Faculty;

@Repository
public interface FacultyDao extends CrudRepository<Faculty, Integer> {
    
    public Optional<Faculty> findByName(String name);
}
