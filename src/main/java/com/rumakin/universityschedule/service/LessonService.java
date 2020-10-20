package com.rumakin.universityschedule.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumakin.universityschedule.dao.LessonDao;
import com.rumakin.universityschedule.exception.*;
import com.rumakin.universityschedule.model.*;

@Service
public class LessonService {

    private LessonDao lessonDao;
    private Logger logger = LoggerFactory.getLogger(LessonService.class);

    @Autowired
    public LessonService(LessonDao lessonDao) {
        logger.info("Construct with {}", lessonDao);
        this.lessonDao = lessonDao;
    }

    public List<Lesson> findAll() {
        logger.debug("findAll() starts");
        List<Lesson> lessons = (List<Lesson>) lessonDao.findAll();
        logger.trace("found {} lessons.", lessons.size());
        return lessons;
    }

    public Lesson findById(int id) {
        logger.debug("find() id {}.", id);
        Lesson lesson = lessonDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Lesson with id %d not found", id)));
        logger.trace("foundById {}, {}.", id, lesson);
        return lesson;
    }

    public Lesson findByAuditoriumIdAndDateAndTimeSlot(int auditoriumId, LocalDate date, int timeSlotId) {
        logger.debug("findByAuditoriumAndDateAndTimeSlot() {}, {}, {}.", date, timeSlotId);
        Lesson lesson = lessonDao.findByAuditoriumIdAndDateAndTimeSlotId(auditoriumId, date, timeSlotId);
        logger.trace("foundByDateAndTimeSlot {}, {}, {}.", auditoriumId, date, timeSlotId);
        return lesson;
    }

    public Lesson findByGroupIdAndDateAndTimeSlot(int groupId, LocalDate date, int timeSlotId) {
        logger.debug("findByGroupIdAndDateAndTimeSlot() {}, {}, {}.", groupId, date, timeSlotId);
        Lesson lesson = lessonDao.findByGroupIdAndDateAndTimeSlotId(groupId, date, timeSlotId);
        logger.trace("foundGroupIdAndDateAndTimeSlot {}, {}, {}.", groupId, date, timeSlotId);
        return lesson;
    }

    public Lesson findByTeacherIdAndDateAndTimeSlot(int teacherId, LocalDate date, int timeSlotId) {
        logger.debug("findByTeacherIdAndDateAndTimeSlot() {}, {}, {}.", teacherId, date, timeSlotId);
        Lesson lesson = lessonDao.findByGroupIdAndDateAndTimeSlotId(teacherId, date, timeSlotId);
        logger.trace("foundByTeacherIdAndDateAndTimeSlot {}, {}, {}.", teacherId, date, timeSlotId);
        return lesson;
    }

    public Lesson add(Lesson lesson) {
        logger.debug("add() {}.", lesson);
        if (lesson.getId() != 0) {
            throw new InvalidEntityException("Id must be 0 for create");
        }
        return lessonDao.save(lesson);
    }

    public Lesson update(Lesson lesson) {
        logger.debug("update() {}.", lesson);
        lesson = lessonDao.save(lesson);
        logger.trace("lesson {} was updated.", lesson);
        return lesson;
    }

    public void delete(int id) {
        logger.debug("delete() id {}.", id);
        lessonDao.deleteById(id);
    }

}
