package com.rumakin.universityschedule.dao;

import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.Building;

@Repository
public interface BuildingDao extends Dao<Building, Integer> {
}
