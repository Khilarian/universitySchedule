package com.rumakin.universityschedule.service;

import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumakin.universityschedule.dao.FacultyDao;
import com.rumakin.universityschedule.exception.InvalidEntityException;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.Faculty;

@Service
public class FacultyService {

    private FacultyDao facultyDao;
    private Logger logger = LoggerFactory.getLogger(FacultyService.class);

    @Autowired
    public FacultyService(FacultyDao facultyDao) {
        this.facultyDao = facultyDao;
    }

    public List<Faculty> findAll() {
        logger.debug("findAll() faculties.");
        List<Faculty> facultys = (List<Faculty>) facultyDao.findAll();
        logger.trace("findAll() result: {} faculties.", facultys.size());
        return facultys;
    }

    public Faculty findById(int id) {
        logger.debug("findById() id {}.", id);
        Faculty faculty = facultyDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Faculty with id %d not found.", id)));
        logger.trace("findById() {} result: {}.", id, faculty);
        return faculty;
    }

    public Faculty findByName(String name) {
        logger.debug("findByName() {}.", name);
        Faculty faculty = facultyDao.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Faculty with name %s not found.", name)));
        logger.trace("findByName() {} result: {}.", name, faculty);
        return faculty;
    }

    public Faculty add(Faculty faculty) {
        logger.debug("add() {}.", faculty);
        if (faculty.getId() != 0) {
            logger.warn("add() fault: faculty {} was not added, with incorrect id {}.", faculty, faculty.getId());
            throw new InvalidEntityException("Id must be 0 for create.");
        }
        faculty = facultyDao.save(faculty);
        logger.trace("faculty {} was added.", faculty);
        return faculty;
    }

    public Faculty update(Faculty faculty) {
        logger.debug("update() {}.", faculty);
        if (faculty.getId() == 0) {
            logger.warn("update() fault: faculty {} was not updated, with incorrect id {}.", faculty, faculty.getId());
            throw new InvalidEntityException("Id must be greater than 0 to update.");
        }
        faculty = facultyDao.save(faculty);
        logger.trace("faculty {} was updated.", faculty);
        return faculty;
    }

    public void deleteById(int id) {
        logger.debug("deleteById() id {}.", id);
        facultyDao.deleteById(id);
    }
}
