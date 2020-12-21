package com.rumakin.universityschedule.service;

import java.time.LocalDate;
import java.util.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumakin.universityschedule.dao.LessonDao;
import com.rumakin.universityschedule.dto.LessonDto;
import com.rumakin.universityschedule.exception.*;
import com.rumakin.universityschedule.model.*;

@Service
public class LessonService {

    private LessonDao lessonDao;
    private CourseService courseService;
    private LessonTypeService lessonTypeService;
    private TimeSlotService timeSlotService;
    private AuditoriumService auditoriumService;
    private TeacherService teacherService;
    private GroupService groupService;

    private Logger logger = LoggerFactory.getLogger(LessonService.class);

    @Autowired
    public LessonService(LessonDao lessonDao, CourseService courseService, LessonTypeService lessonTypeService,
            TimeSlotService timeSlotService, AuditoriumService auditoriumService, TeacherService teacherService,
            GroupService groupService) {
        logger.info("Construct with {}", lessonDao, courseService, lessonTypeService, timeSlotService,
                auditoriumService, teacherService, groupService);
        this.lessonDao = lessonDao;
        this.courseService = courseService;
        this.lessonTypeService = lessonTypeService;
        this.timeSlotService = timeSlotService;
        this.auditoriumService = auditoriumService;
        this.teacherService = teacherService;
        this.groupService = groupService;
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

    public Lesson add(Lesson lesson) {
        logger.debug("add() {}.", lesson);
        if (lesson.getId() != 0) {
            logger.warn("add() fault: lesson {} was not added, with incorrect id {}.", lesson, lesson.getId());
            throw new InvalidEntityException("Id must be 0 for create.");
        }
        lesson = lessonDao.save(lesson);
        logger.trace("lesson {} was added.", lesson);
        return lesson;
    }

    public Lesson update(Lesson lesson) {
        logger.debug("update() {}.", lesson);
        if (lesson.getId() == 0) {
            logger.warn("update() fault: lesson {} was not updated, with incorrect id {}.", lesson, lesson.getId());
            throw new InvalidEntityException("Id must be greater than 0 to update.");
        }
        lesson = lessonDao.save(lesson);
        logger.trace("lesson {} was updated.", lesson);
        return lesson;
    }

    public void deleteById(int id) {
        logger.debug("delete() id {}.", id);
        lessonDao.deleteById(id);
    }

    public List<Course> getCourses() {
        return courseService.findAll();
    }

    public List<LessonType> getLessonTypes() {
        return lessonTypeService.findAll();
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlotService.findAll();
    }

    public List<Auditorium> getAuditoriums() {
        return auditoriumService.findAll();
    }

    public List<Teacher> getTeachers() {
        return teacherService.findAll();
    }

    public List<Group> getGroups() {
        return groupService.findAll();
    }

    public String getBuildingName(int auditoriumId) {
        return auditoriumService.findById(auditoriumId).getBuilding().getName();
    }

    public boolean isAuditoriumFree(int auditoriumId, Integer lessonId, LocalDate date, int timeSlotId) {
        logger.debug("isAuditoriumFree() with {}, {}, {}, {}.", auditoriumId, lessonId, date, timeSlotId);
        boolean result = lessonDao.isAuditoriumFree(auditoriumId, lessonId, date, timeSlotId);
        logger.trace("isAuditoriumFree() result: {} .", result);
        return result;
    }

    public Set<Integer> getBusyGroupsId(Integer lessonId, LocalDate date, int timeSlotId) {
        logger.debug("getBusyGroupsId() with {}, {}, {}.", lessonId, date, timeSlotId);
        Set<Integer> result = lessonDao.getBusyGroupsId(lessonId, date, timeSlotId);
        logger.trace("getBusyGroupsId() result: {} .", result);
        return result;
    }

    public Set<Integer> getBusyTeachersId(Integer lessonId, LocalDate date, int timeSlotId) {
        logger.debug("getBusyTeachersId() with {}, {}, {}.", lessonId, date, timeSlotId);
        Set<Integer> result = lessonDao.getBusyTeachersId(lessonId, date, timeSlotId);
        logger.trace("getBusyTeachersId() result: {} .", result);
        return result;
    }

    public List<LessonDto> findMonthScheduleForTeacher(Integer teacherId, LocalDate startDate, LocalDate endDate) {
        logger.debug("findMonthScheduleForTeacher() with {}, {}, {}.", teacherId, startDate, endDate);
        List<LessonDto> result = lessonDao.findMonthScheduleForTeacher(teacherId, startDate, endDate);
        logger.trace("findMonthScheduleForTeacher() result: {}", result);
        return result;
    }

    public List<LessonDto> findMonthScheduleForGroup(Integer groupId, LocalDate startDate, LocalDate endDate) {
        logger.debug("findMonthScheduleForGroup() with {}, {}, {}.", groupId, startDate, endDate);
        List<LessonDto> result = lessonDao.findMonthScheduleForGroup(groupId, startDate, endDate);
        logger.trace("findMonthScheduleForGroup() result: {}", result);
        return result;
    }

    public List<LessonDto> findDayScheduleForTeacher(Integer teacherId, LocalDate date) {
        logger.debug("findDayScheduleForTeacher() with {}, {}.", teacherId, date);
        List<LessonDto> result = lessonDao.findDayScheduleForTeacher(teacherId, date);
        logger.trace("findDayScheduleForTeacher() result: {}", result);
        return result;
    }

    public List<LessonDto> findDayScheduleForGroup(Integer groupId, LocalDate date) {
        logger.debug("findDayScheduleForGroup() with {}, {}.", groupId, date);
        List<LessonDto> result = lessonDao.findDayScheduleForGroup(groupId, date);
        logger.trace("findDayScheduleForGroup() result: {}", result);
        return result;
    }

}
