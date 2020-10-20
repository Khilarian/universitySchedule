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
        logger.trace("found {} lessonTypes.", lessonTypes.size());
        return lessonTypes;
    }

    public LessonType findById(int id) {
        logger.debug("findById() id {}.", id);
        LessonType lessonType = lessonTypeDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("LessonType with id %d not found", id)));
        logger.trace("found {}.", lessonType);
        return lessonType;
    }

    public LessonType findByName(String name) {
        logger.debug("findByName() name {}.", name);
        LessonType lessonType = lessonTypeDao.findByName(name);
        logger.trace("found {}.", lessonType);
        return lessonType;
    }

    public LessonType add(LessonType lessonType) {
        logger.debug("add() {}.", lessonType);
        if (lessonType.getId() != 0) {
            throw new InvalidEntityException("Id must be 0 for create");
        }
        return lessonTypeDao.save(lessonType);
    }

    public LessonType update(LessonType lessonType) {
        logger.debug("update() {}.", lessonType);
        lessonType = lessonTypeDao.save(lessonType);
        logger.trace("lessonType {} was updated.", lessonType);
        return lessonType;
    }

    public void delete(int id) {
        logger.debug("delete() id {}.", id);
        lessonTypeDao.deleteById(id);
    }
}
