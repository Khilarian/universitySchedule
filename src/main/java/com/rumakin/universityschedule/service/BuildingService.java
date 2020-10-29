package com.rumakin.universityschedule.service;

import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumakin.universityschedule.dao.BuildingDao;
import com.rumakin.universityschedule.exception.*;
import com.rumakin.universityschedule.model.Building;

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
        logger.trace("findAll() result: {} buildings.", buildings.size());
        return buildings;
    }

    public Building findById(int id) {
        logger.debug("findById() id {}.", id);
        Building building = buildingDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Building with id %d not found.", id)));
        logger.trace("findById() {} result: {}.", id, building);
        return building;
    }

    public Building findByName(String name) {
        logger.debug("findByName() {}.", name);
        Building building = buildingDao.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Building with name %s not found.", name)));
        logger.trace("findByName() {} result: {}.", name, building);
        return building;
    }

    public Building findByAddress(String address) {
        logger.debug("findByAddress() {}.", address);
        Building building = buildingDao.findByAddress(address)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Building with address %s not found.", address)));
        logger.trace("findByAddress() {} result: {}.", address, building);
        return building;
    }

    public Building add(Building building) {
        logger.debug("add() {}.", building);
        if (building.getId() != 0) {
            throw new InvalidEntityException("Id must be 0 for create.");
        }
        return buildingDao.save(building);
    }

    public Building update(Building building) {
        logger.debug("update() {}.", building);
        if (building.getId() == 0) {
            logger.warn("update() fault: building {} was not updated, with incorrect id {}.", building,
                    building.getId());
            throw new InvalidEntityException("Id must be greater than 0 to update.");
        }
        building = buildingDao.save(building);
        logger.trace("building {} was updated.", building);
        return building;
    }

    public void deleteById(int id) {
        logger.debug("delete() id {}.", id);
        buildingDao.deleteById(id);
    }
}
