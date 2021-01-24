package com.rumakin.universityschedule.service;

import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumakin.universityschedule.dao.LessonTypeDao;
import com.rumakin.universityschedule.exception.*;
import com.rumakin.universityschedule.model.*;

@Service
public class LessonTypeService {

    private LessonTypeDao lessonTypeDao;
    private Logger logger = LoggerFactory.getLogger(LessonTypeService.class);

    @Autowired
    public LessonTypeService(LessonTypeDao lessonTypeDao) {
        this.lessonTypeDao = lessonTypeDao;
    }

    public List<LessonType> findAll() {
        logger.debug("findAll() lessonTypes.");
        List<LessonType> lessonTypes = (List<LessonType>) lessonTypeDao.findAll();
        logger.trace("findAll() result: {} lessonTypes.", lessonTypes.size());
        return lessonTypes;
    }

    public LessonType findById(int id) {
        logger.debug("findById() id {}.", id);
        LessonType lessonType = lessonTypeDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("LessonType with id %d not found.", id)));
        logger.trace("findById() {} result: {}.", id, lessonType);
        return lessonType;
    }

    public LessonType findByName(String name) {
        logger.debug("findByName() name {}.", name);
        LessonType lessonType = lessonTypeDao.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException(String.format("LessonType with name %s not found.", name)));
        logger.trace("findByName() {} result: {}.", name, lessonType);
        return lessonType;
    }

    public LessonType add(LessonType lessonType) {
        logger.debug("add() {}.", lessonType);
        if (lessonType.getId() != 0) {
            logger.warn("add() fault: lessonType {} was not added, with incorrect id {}.", lessonType, lessonType.getId());
            throw new InvalidEntityException("Id must be 0 for create.");
        }
        lessonType = lessonTypeDao.save(lessonType);
        logger.trace("lessonType {} was added.", lessonType);
        return lessonType;
    }

    public LessonType update(LessonType lessonType) {
        logger.debug("update() {}.", lessonType);
        if (lessonType.getId() == 0) {
            logger.warn("update() fault: lessonType {} was not updated, with incorrect id {}.", lessonType, lessonType.getId());
            throw new InvalidEntityException("Id must be greater than 0 to update.");
        }
        lessonType = lessonTypeDao.save(lessonType);
        logger.trace("lessonType {} was updated.", lessonType);
        return lessonType;
    }

    public void deleteById(int id) {
        logger.debug("deleteById() id {}.", id);
        lessonTypeDao.deleteById(id);
    }
}
