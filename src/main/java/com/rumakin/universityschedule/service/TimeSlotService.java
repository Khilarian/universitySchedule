package com.rumakin.universityschedule.service;

import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumakin.universityschedule.dao.*;
import com.rumakin.universityschedule.exception.*;
import com.rumakin.universityschedule.model.*;

@Service
public class TimeSlotService {

    private TimeSlotDao timeSlotDao;
    private Logger logger = LoggerFactory.getLogger(TimeSlotService.class);

    @Autowired
    public TimeSlotService(TimeSlotDao timeSlotDao) {
        this.timeSlotDao = timeSlotDao;
    }

    public List<TimeSlot> findAll() {
        logger.debug("findAll() timeSlots.");
        List<TimeSlot> timeSlots = (List<TimeSlot>) timeSlotDao.findAll();
        logger.trace("findAll() result: {} timeSlots.", timeSlots.size());
        return timeSlots;
    }

    public TimeSlot findById(int id) {
        logger.debug("find() id {}.", id);
        TimeSlot timeSlot = timeSlotDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("TimeSlot with id %d not found.", id)));
        logger.trace("findById() {} result: {}.", id, timeSlot);
        return timeSlot;
    }

    public TimeSlot findByNumber(int number) {
        logger.debug("findByNumber() number {}.", number);
        TimeSlot timeSlot = timeSlotDao.findByNumber(number).orElseThrow(
                () -> new ResourceNotFoundException(String.format("TimeSlot with number %d not found.", number)));
        logger.trace("findByNumber() {} result {}.", number, timeSlot);
        return timeSlot;
    }

    public TimeSlot add(TimeSlot timeSlot) {
        logger.debug("add() {}.", timeSlot);
        if (timeSlot.getId() != 0) {
            logger.warn("add() fault: timeSlot {} was not added, with incorrect id {}.", timeSlot, timeSlot.getId());
            throw new InvalidEntityException("Id must be 0 for create.");
        }
        timeSlot = timeSlotDao.save(timeSlot);
        logger.trace("timeSlot {} was added.", timeSlot);
        return timeSlot;
    }

    public TimeSlot update(TimeSlot timeSlot) {
        logger.debug("update() {}.", timeSlot);
        if (timeSlot.getId() == 0) {
            logger.warn("update() fault: timeSlot {} was not updated, with incorrect id {}.", timeSlot, timeSlot.getId());
            throw new InvalidEntityException("Id must be greater than 0 to update.");
        }
        timeSlot = timeSlotDao.save(timeSlot);
        logger.trace("timeSlot {} was updated.", timeSlot);
        return timeSlot;
    }

    public void deleteById(int id) {
        logger.debug("deleteById() id {}.", id);
        timeSlotDao.deleteById(id);
    }
}
