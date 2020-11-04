package com.rumakin.universityschedule.service;

import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumakin.universityschedule.dao.StudentDao;
import com.rumakin.universityschedule.exception.*;
import com.rumakin.universityschedule.model.*;

@Service
public class StudentService {

    private StudentDao studentDao;
    private GroupService groupService;
    private Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    public StudentService(StudentDao studentDao, GroupService groupService) {
        this.studentDao = studentDao;
        this.groupService = groupService;
    }

    public List<Student> findAll() {
        logger.debug("findAll() students.");
        List<Student> students = (List<Student>) studentDao.findAll();
        logger.trace("findAll() result: {} students.", students.size());
        return students;
    }

    public Student findById(int id) {
        logger.debug("findById() id {}.", id);
        Student student = studentDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Student with id %d not found.", id)));
        logger.trace("findById() {} result: {}.", id, student);
        return student;
    }

    public Student findByEmail(String email) {
        logger.debug("findByEmail() {}.", email);
        Student student = studentDao.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Student with email %s not found.", email)));
        logger.trace("findByName() {} result: {}.", email, student);
        return student;
    }

    public Student findByPhone(String phone) {
        logger.debug("findByPhone() {}.", phone);
        Student student = studentDao.findByPhone(phone)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Student with phone %s not found.", phone)));
        logger.trace("findByAddress() {} result: {}.", phone, student);
        return student;
    }

    public Student add(Student student) {
        logger.debug("add() {}.", student);
        if (student.getId() != 0) {
            logger.warn("add() fault: student {} was not added, with incorrect id {}.", student, student.getId());
            throw new InvalidEntityException("Id must be 0 for create.");
        }
        student = studentDao.save(student);
        logger.trace("student {} was added.", student);
        return student;
    }

    public Student update(Student student) {
        logger.debug("update() {}.", student);
        if (student.getId() == 0) {
            logger.warn("update() fault: student {} was not updated, with incorrect id {}.", student, student.getId());
            throw new InvalidEntityException("Id must be greater than 0 to update.");
        }
        student = studentDao.save(student);
        logger.trace("student {} was updated.", student);
        return student;
    }

    public void deleteById(int id) {
        logger.debug("delete() id {}.", id);
        studentDao.deleteById(id);
    }

    public List<Group> getGroups() {
        return groupService.findAll();
    }
}
