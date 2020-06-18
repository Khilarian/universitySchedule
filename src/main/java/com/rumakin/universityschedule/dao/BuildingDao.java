package com.rumakin.universityschedule.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.model.Building;

@Repository
public interface BuildingDao extends CrudRepository<Building, Integer> {

    public Building findByName(String name);

    public Building findByAddress(String address);
}
