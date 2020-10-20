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
        logger.trace("found {} students.", students.size());
        return students;
    }

    public Student findById(int id) {
        logger.debug("find() id {}.", id);
        Student student = studentDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Student with id %d not found", id)));
        logger.trace("found {}.", student);
        return student;
    }

    public Student findByEmail(String email) {
        logger.debug("findByEmail() {}.", email);
        Student student = studentDao.findByEmail(email);
        logger.trace("foundByName {}, {}.", email, student);
        return student;
    }

    public Student findByPhone(String phone) {
        logger.debug("findByPhone() {}.", phone);
        Student student = studentDao.findByPhone(phone);
        logger.trace("foundByAddress {}, {}.", phone, student);
        return student;
    }

    public Student add(Student student) {
        logger.debug("add() {}.", student);
        if (student.getId()!= 0) {
            throw new InvalidEntityException("Id must be 0 for create");
        }
        return studentDao.save(student);
    }

    public Student update(Student student) {
        logger.debug("update() {}.", student);
        student = studentDao.save(student);
        logger.trace("student {} was updated.", student);
        return student;
    }

    public void delete(int id) {
        logger.debug("delete() id {}.", id);
        studentDao.deleteById(id);
    }
    
    public List<Group> getGroups(){
        return groupService.findAll();
    }
}
