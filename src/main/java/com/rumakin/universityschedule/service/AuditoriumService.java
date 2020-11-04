package com.rumakin.universityschedule.service;

import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumakin.universityschedule.dao.AuditoriumDao;
import com.rumakin.universityschedule.exception.InvalidEntityException;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.*;

@Service
public class AuditoriumService {

    private AuditoriumDao auditoriumDao;
    private BuildingService buildingService;
    private Logger logger = LoggerFactory.getLogger(AuditoriumService.class);

    @Autowired
    public AuditoriumService(AuditoriumDao auditoriumDao, BuildingService buildingService) {
        this.auditoriumDao = auditoriumDao;
        this.buildingService = buildingService;
    }

    public List<Auditorium> findAll() {
        logger.debug("findAll() auditoriums.");
        List<Auditorium> auditoriums = (List<Auditorium>) auditoriumDao.findAll();
        logger.trace("findAll() result: {} auditoriums.", auditoriums.size());
        return auditoriums;
    }

    public Auditorium findById(int id) {
        logger.debug("findById() id {}", id);
        Auditorium auditorium = auditoriumDao.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Auditorium with id %d not found.", id)));
        logger.trace("findById() {} result: {}", id, auditorium);
        return auditorium;
    }

    public Auditorium findByNumberAndBuildingId(int number, int buildingId) {
        logger.debug("findByNumberAndBuildingId() {},{}.", number, buildingId);
        Auditorium auditorium = auditoriumDao.findByNumberAndBuildingId(number, buildingId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Auditorium with number %d in building %d not found.", number, buildingId)));
        logger.trace("findByNumberAndBuildingId() {}, {} result: {}.", number, buildingId, auditorium);
        return auditorium;
    }

    public Auditorium add(Auditorium auditorium) {
        logger.debug("add() {}", auditorium);
        if (auditorium.getId() != 0) {
            logger.warn("add() fault: auditorium {} was not added, with incorrect id {}.", auditorium,
                    auditorium.getId());
            throw new InvalidEntityException("Id must be 0 for create.");
        }
        auditorium = auditoriumDao.save(auditorium);
        logger.trace("auditorium {} was added.", auditorium);
        return auditorium;
    }

    public Auditorium update(Auditorium auditorium) {
        logger.debug("update() {}.", auditorium);
        if (auditorium.getId() == 0) {
            logger.warn("update() fault: auditorium {} was not updated, with incorrect id {}.", auditorium,
                    auditorium.getId());
            throw new InvalidEntityException("Id must be greater than 0 to update.");
        }
        auditorium = auditoriumDao.save(auditorium);
        logger.trace("auditorium {} was updated.", auditorium);
        return auditorium;
    }

    public void deleteById(int id) {
        logger.debug("delete() id {}.", id);
        auditoriumDao.deleteById(id);
    }

    public List<Building> getBuildings() {
        return buildingService.findAll();
    }

}
