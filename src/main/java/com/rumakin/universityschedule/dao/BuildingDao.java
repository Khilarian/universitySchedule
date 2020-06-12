package com.rumakin.universityschedule.dao;

import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.model.Building;

@Repository
public interface BuildingDao extends Dao<Building, Integer> {

    public Building findByName(String name);
    
    public Building findByAddress(String address);
}
