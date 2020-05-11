package com.rumakin.universityschedule.service;

import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumakin.universityschedule.dao.BuildingDao;
import com.rumakin.universityschedule.models.Building;

@Service
public class BuildingService {

    private BuildingDao buildingDao;
    private Logger logger = LoggerFactory.getLogger(BuildingService.class);

    @Autowired
    public BuildingService(BuildingDao buildingDao) {
        this.buildingDao = buildingDao;
    }

    public List<Building> findAll() {
        logger.debug("findAll() buildings.");
        List<Building> buildings = (List<Building>) buildingDao.findAll();
        logger.trace("found {} buildings.", buildings.size());
        return buildings;
    }

    public Building find(int id) {
        logger.debug("find() id {}.", id);
        Building building = buildingDao.findById(id).get();
        logger.trace("found {}.", building);
        return building;
    }

    public void add(Building building) {
        logger.debug("add() {}.", building);
        buildingDao.save(building);
    }

    public void update(Building building) {
        logger.debug("update() {}.", building);
        buildingDao.save(building);
        logger.trace("building {} was updated.", building);
    }

    public void delete(int id) {
        logger.debug("delete() id {}.", id);
        buildingDao.deleteById(id);
    }
}
