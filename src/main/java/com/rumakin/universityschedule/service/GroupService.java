package com.rumakin.universityschedule.service;

import java.util.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumakin.universityschedule.dao.GroupDao;
import com.rumakin.universityschedule.models.Group;

@Service
public class GroupService {

    private GroupDao groupDao;
    private Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public List<Group> findAll() {
        logger.debug("findAll() starts");
        List<Group> groups = groupDao.findAll();
        logger.trace("found {} groups.", groups.size());
        return groups;
    }

    public Group find(int id) {
        logger.debug("find() id {}.", id);
        Group group = groupDao.find(id);
        logger.trace("found {}.", group);
        return group;
    }

    public void add(Group group) {
        logger.debug("add() {}.", group);
        // int id = groupDao.add(group).getId();
        // logger.trace("group was added, id={}.", id);
    }

    public void update(Group group) {
        logger.debug("update() {}.", group);
        groupDao.update(group);
        logger.trace("grup {} was updated.", group);
    }

    public void delete(int id) {
        logger.debug("delete() id {}.", id);
        groupDao.delete(id);
    }
}
