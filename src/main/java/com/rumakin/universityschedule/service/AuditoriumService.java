package com.rumakin.universityschedule.service;

import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumakin.universityschedule.dao.AuditoriumDao;
import com.rumakin.universityschedule.models.*;

@Service
public class AuditoriumService {

    private AuditoriumDao auditoriumDao;
    private Logger logger = LoggerFactory.getLogger(AuditoriumService.class);

    @Autowired
    public AuditoriumService(AuditoriumDao auditoriumDao) {
        logger.info("Construct with {}", auditoriumDao);
        this.auditoriumDao = auditoriumDao;
    }

    public List<Auditorium> findAll() {
        logger.debug("findAll() auditoriums");
        List<Auditorium> auditoriums = (List<Auditorium>) auditoriumDao.findAll();
        logger.trace("found {} auditoriums", auditoriums.size());
        return auditoriums;
    }

    public Auditorium find(int id) {
        logger.debug("find() id {}", id);
        Auditorium auditorium = auditoriumDao.findById(id).get();
        logger.trace("found {}", auditorium);
        return auditorium;
    }

    public Auditorium add(Auditorium auditorium) {
        logger.debug("add() {}", auditorium);
        return auditoriumDao.save(auditorium);
    }

    public Auditorium update(Auditorium auditorium) {
        logger.debug("update() {}.", auditorium);
        Auditorium updateAuditorium = auditoriumDao.save(auditorium);
        logger.trace("auditorium {} was updated.", auditorium);
        return updateAuditorium;
    }

    public void delete(int id) {
        logger.debug("delete() id {}.", id);
        auditoriumDao.deleteById(id);
    }

}
