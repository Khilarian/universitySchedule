package com.rumakin.universityschedule.service;

import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumakin.universityschedule.dao.FacultyDao;
import com.rumakin.universityschedule.exceptions.ResourceNotFoundException;
import com.rumakin.universityschedule.models.Faculty;

@Service
public class FacultyService {

    private FacultyDao facultyDao;
    private Logger logger = LoggerFactory.getLogger(FacultyService.class);

    @Autowired
    public FacultyService(FacultyDao facultyDao) {
        this.facultyDao = facultyDao;
    }

    public List<Faculty> findAll() {
        logger.debug("findAll() facultys.");
        List<Faculty> facultys = (List<Faculty>) facultyDao.findAll();
        logger.trace("found {} faculties.", facultys.size());
        return facultys;
    }

    public Faculty find(int id) {
        logger.debug("find() id {}.", id);
        Faculty faculty = facultyDao.findById(id).orElseThrow(()->new ResourceNotFoundException(String.format("Faculty with id %d not found", id)));
        logger.trace("found {}.", faculty);
        return faculty;
    }

    public Faculty add(Faculty faculty) {
        logger.debug("add() {}.", faculty);
        return facultyDao.save(faculty);
    }

    public Faculty update(Faculty faculty) {
        logger.debug("update() {}.", faculty);
        faculty = facultyDao.save(faculty);
        logger.trace("faculty {} was updated.", faculty);
        return faculty;
    }

    public void delete(int id) {
        logger.debug("delete() id {}.", id);
        facultyDao.deleteById(id);
    }
}
