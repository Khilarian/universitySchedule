package com.rumakin.universityschedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.rumakin.universityschedule.dao.BuildingDao;
import com.rumakin.universityschedule.models.Building;

@Service
public class BuildingService {

    private final BuildingDao buildingDao;
    
    @Autowired
    public BuildingService(BuildingDao buildingDao) {
        this.buildingDao = buildingDao;
    }
    
    public void add(Building building) {
        buildingDao.add(building);
    }
    
    public void addAll(List<Building> buildings) {
        buildingDao.addAll(buildings);
    }
    
    public Building find(int id) {
        return buildingDao.find(id);
    }
    
    public List<Building> findAll(){
        return buildingDao.findAll();
    }
    
    public void remove(int id) {
        buildingDao.remove(id);
    }
}
