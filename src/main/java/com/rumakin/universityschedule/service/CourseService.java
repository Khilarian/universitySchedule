package com.rumakin.universityschedule.service;

import java.util.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumakin.universityschedule.dao.CourseDao;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.*;

@Service
public class CourseService {

    private CourseDao courseDao;
    private FacultyService facultyService;
    private Logger logger = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    public CourseService(CourseDao courseDao, FacultyService facultyService) {
        this.courseDao = courseDao;
        this.facultyService = facultyService;
    }

    public List<Course> findAll() {
        logger.debug("findAll() starts");
        List<Course> courses = (List<Course>) courseDao.findAll();
        logger.trace("found {} courses.", courses.size());
        return courses;
    }

    public Course findById(int id) {
        logger.debug("find() id {}.", id);
        Course course = courseDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Course with id %d not found", id)));
        logger.trace("foundById {}, {}.", id, course);
        return course;
    }

    public Course findByName(String name) {
        logger.debug("findByName() {}.", name);
        Course course = courseDao.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Course with name %s not found", name)));
        logger.trace("foundByName {}, {}.", name, course);
        return course;
    }

    public Course add(Course course) {
        logger.debug("add() {}.", course);
        return courseDao.save(course);
    }

    public Course update(Course course) {
        logger.debug("update() {}.", course);
        course = courseDao.save(course);
        logger.trace("course {} was updated.", course);
        return course;
    }

    public void delete(int id) {
        logger.debug("delete() id {}.", id);
        courseDao.deleteById(id);
    }

    public List<Faculty> getFaculties() {
        return facultyService.findAll();
    }
}
