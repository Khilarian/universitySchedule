package com.rumakin.universityschedule.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.model.Building;

@Repository
public interface BuildingDao extends CrudRepository<Building, Integer> {

    public Optional<Building> findByName(String name);

    public Optional<Building> findByAddress(String address);
}
