package com.rumakin.universityschedule.service;

import java.time.LocalDate;
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

    public List<Auditorium> findAuditoriumsForGroupOnDate(Group group, LocalDate date) {
        logger.debug("findAuditoriumsForGroupOnDate() for group{} date {}", group, date);
        int groupId = group.getId();
        List<Auditorium> auditoriums = auditoriumDao.findAuditoriumOnDate(groupId, date);
        logger.trace("found {} auditoriums", auditoriums.size());
        return auditoriums;
    }

}
