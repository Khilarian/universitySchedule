package com.rumakin.universityschedule.service;

import java.util.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rumakin.universityschedule.dao.GroupDao;
import com.rumakin.universityschedule.models.*;

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
        logger.debug("findAll() starts");
        List<Group> groups = (List<Group>) groupDao.findAll();
        logger.trace("found {} groups.", groups.size());
        return groups;
    }

    public Group find(int id) {
        logger.debug("find() id {}.", id);
        Group group = groupDao.findById(id).orElse(null);
        logger.trace("found {}.", group);
        return group;
    }

    public void add(Group group) {
        logger.debug("add() {}.", group);
        groupDao.save(group);
    }

    @Transactional
    public void update(Group group) {
        logger.debug("update() {}.", group);
        groupDao.save(group);
        logger.trace("group {} was updated.", group);
    }

    public void delete(int id) {
        logger.debug("delete() id {}.", id);
        groupDao.deleteById(id);
    }
    
    public List<Faculty> getFaculties(){
        return facultyService.findAll();
    }
}
