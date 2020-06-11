package com.rumakin.universityschedule.dao;

import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.Faculty;

@Repository
public interface FacultyDao extends Dao<Faculty, Integer> {
    
    public Faculty findByName(String name);
}
