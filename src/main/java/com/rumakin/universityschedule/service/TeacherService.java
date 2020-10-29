package com.rumakin.universityschedule.service;

import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumakin.universityschedule.dao.TeacherDao;
import com.rumakin.universityschedule.exception.*;
import com.rumakin.universityschedule.model.*;

@Service
public class TeacherService {

    private TeacherDao teacherDao;
    private FacultyService facultyService;
    private CourseService courseService;
    private Logger logger = LoggerFactory.getLogger(TeacherService.class);

    @Autowired
    public TeacherService(TeacherDao teacherDao, FacultyService facultyService, CourseService courseService) {
        this.teacherDao = teacherDao;
        this.facultyService = facultyService;
        this.courseService = courseService;
    }

    public List<Teacher> findAll() {
        logger.debug("findAll() teachers.");
        List<Teacher> teachers = (List<Teacher>) teacherDao.findAll();
        logger.trace("found {} teachers.", teachers.size());
        return teachers;
    }

    public Teacher findById(int id) {
        logger.debug("find() id {}.", id);
        Teacher teacher = teacherDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Teacher with id %d not found", id)));
        logger.trace("found {}.", teacher);
        return teacher;
    }

    public Teacher findByEmail(String email) {
        logger.debug("findByEmail() {}.", email);
        Teacher teacher = teacherDao.findByEmail(email);
        logger.trace("foundByName {}, {}.", email, teacher);
        return teacher;
    }

    public Teacher findByPhone(String phone) {
        logger.debug("findByPhone() {}.", phone);
        Teacher teacher = teacherDao.findByPhone(phone);
        logger.trace("foundByAddress {}, {}.", phone, teacher);
        return teacher;
    }

    public Teacher add(Teacher teacher) {
        logger.debug("add() {}.", teacher);
        if (teacher.getId() != 0) {
            throw new InvalidEntityException("Id must be 0 for create");
        }
        return teacherDao.save(teacher);
    }

    public Teacher update(Teacher teacher) {
        logger.debug("update() {}.", teacher);
        teacher = teacherDao.save(teacher);
        logger.trace("teacher {} was updated.", teacher);
        return teacher;
    }

    public void deleteById(int id) {
        logger.debug("delete() id {}.", id);
        teacherDao.deleteById(id);
    }
    
    public List<Faculty> getFaculties(){
        return facultyService.findAll();
    }
    
    public List<Course> getCourses(){
        return courseService.findAll();
    }
}
