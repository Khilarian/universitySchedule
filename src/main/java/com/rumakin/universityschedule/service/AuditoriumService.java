package com.rumakin.universityschedule.service;

import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumakin.universityschedule.dao.AuditoriumDao;
import com.rumakin.universityschedule.exceptions.ResourceNotFoundException;
import com.rumakin.universityschedule.models.*;

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
        logger.debug("findAll() auditoriums");
        List<Auditorium> auditoriums = (List<Auditorium>) auditoriumDao.findAll();
        logger.trace("found {} auditoriums", auditoriums.size());
        return auditoriums;
    }

    public Auditorium find(int id) {
        logger.debug("find() id {}", id);
        Auditorium auditorium = auditoriumDao.findById(id).orElseThrow(()->new ResourceNotFoundException(String.format("Auditorium with id %d not found", id)));
        logger.trace("found {}", auditorium);
        return auditorium;
    }

    public Auditorium add(Auditorium auditorium) {
        logger.debug("add() {}", auditorium);
        return auditoriumDao.save(auditorium);
    }

    public Auditorium update(Auditorium auditorium) {
        logger.debug("update() {}.", auditorium);
        auditorium = auditoriumDao.save(auditorium);
        logger.trace("auditorium {} was updated.", auditorium);
        return auditorium;
    }

    public void delete(int id) {
        logger.debug("delete() id {}.", id);
        auditoriumDao.deleteById(id);
    }

    public List<Building> getBuildings() {
        return buildingService.findAll();
    }

}
