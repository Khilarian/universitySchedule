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
    
}
