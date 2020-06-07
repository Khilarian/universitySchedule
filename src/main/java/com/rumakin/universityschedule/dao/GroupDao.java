package com.rumakin.universityschedule.dao;

import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.*;

@Repository
public interface GroupDao extends Dao<Group, Integer> {
    
    public Group findByName(String name);
}
