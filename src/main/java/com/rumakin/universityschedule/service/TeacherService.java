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
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(TeacherService.class);

    @Autowired
    public TeacherService(TeacherDao teacherDao, FacultyService facultyService, CourseService courseService, UserService userService) {
        this.teacherDao = teacherDao;
        this.facultyService = facultyService;
        this.courseService = courseService;
        this.userService = userService;
    }

    public List<Teacher> findAll() {
        logger.debug("findAll() teachers.");
        List<Teacher> teachers = (List<Teacher>) teacherDao.findAll();
        logger.trace("findAll() result: {} teachers.", teachers.size());
        return teachers;
    }

    public Teacher findById(int id) {
        logger.debug("findById() id {}.", id);
        Teacher teacher = teacherDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Teacher with id %d not found.", id)));
        logger.trace("findById() result: {}.", teacher);
        return teacher;
    }

    public Teacher findByEmail(String email) {
        logger.debug("findByEmail() {}.", email);
        Teacher teacher = teacherDao.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Teacher with email %s not found.", email)));
        logger.trace("findByName() {} result: {}.", email, teacher);
        return teacher;
    }

    public Teacher findByPhone(String phone) {
        logger.debug("findByPhone() {}.", phone);
        Teacher teacher = teacherDao.findByPhone(phone)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Teacher with phone %s not found.", phone)));
        logger.trace("findByAddress() {} result: {}.", phone, teacher);
        return teacher;
    }

    public Teacher add(Teacher teacher) {
        logger.debug("add() {}.", teacher);
        if (teacher.getId() != 0) {
            logger.warn("add() fault: teacher {} was not added, with incorrect id {}.", teacher, teacher.getId());
            throw new InvalidEntityException("Id must be 0 for create");
        }
        userService.add(User.fromPerson(teacher));
        teacher = teacherDao.save(teacher);
        logger.trace("teacher {} was added.", teacher);
        return teacher;
    }

    public Teacher update(Teacher teacher) {
        logger.debug("update() {}.", teacher);
        if (teacher.getId() == 0) {
            logger.warn("update() fault: teacher {} was not updated, with incorrect id {}.", teacher, teacher.getId());
            throw new InvalidEntityException("Id must be greater than 0 to update.");
        }
        userService.update(User.fromPerson(teacher));
        teacher = teacherDao.save(teacher);
        logger.trace("teacher {} was updated.", teacher);
        return teacher;
    }

    public void deleteById(int id) {
        logger.debug("deleteById() id {}.", id);
        userService.deleteByEmail(findById(id).getEmail());
        teacherDao.deleteById(id);
    }
    
    public List<Faculty> getFaculties(){
        return facultyService.findAll();
    }
    
    public List<Course> getCourses(){
        return courseService.findAll();
    }
}
