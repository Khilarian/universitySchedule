package com.rumakin.universityschedule.service;

import java.util.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumakin.universityschedule.dao.GroupDao;
import com.rumakin.universityschedule.exception.InvalidEntityException;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.*;

@Service
public class GroupService {

    private GroupDao groupDao;
    private FacultyService facultyService;
    private Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    public GroupService(GroupDao groupDao, FacultyService facultyService) {
        this.groupDao = groupDao;
        this.facultyService = facultyService;
    }

    public List<Group> findAll() {
        logger.debug("findAll() starts.");
        List<Group> groups = (List<Group>) groupDao.findAll();
        logger.trace("findAll() result: {} groups.", groups.size());
        return groups;
    }

    public Group findById(int id) {
        logger.debug("findById() id {}.", id);
        Group group = groupDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Group with id %d not found.", id)));
        logger.trace("findById() {} result: {}.", id, group);
        return group;
    }

    public Group findByName(String name) {
        logger.debug("findByName() {}.", name);
        Group group = groupDao.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Group with name %s not found.", name)));
        logger.trace("findByName() {} result: {}.", name, group);
        return group;
    }

    public Group add(Group group) {
        logger.debug("add() {}.", group);
        if (group.getId() != 0) {
            throw new InvalidEntityException("Id must be 0 for create.");
        }
        return groupDao.save(group);
    }

    public Group update(Group group) {
        logger.debug("update() {}.", group);
        if (group.getId() == 0) {
            logger.warn("update() fault: group {} was not updated, with incorrect id {}.", group, group.getId());
            throw new InvalidEntityException("Id must be greater than 0 to update.");
        }
        group = groupDao.save(group);
        logger.trace("group {} was updated.", group);
        return group;
    }

    public void deleteById(int id) {
        logger.debug("delete() id {}.", id);
        groupDao.deleteById(id);
    }

    public List<Faculty> getFaculties() {
        return facultyService.findAll();
    }
}
