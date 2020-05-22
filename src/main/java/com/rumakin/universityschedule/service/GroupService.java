package com.rumakin.universityschedule.service;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rumakin.universityschedule.dao.GroupDao;
import com.rumakin.universityschedule.dto.GroupDto;
import com.rumakin.universityschedule.models.Faculty;
import com.rumakin.universityschedule.models.Group;

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

    public List<GroupDto> findAll() {
        logger.debug("findAll() starts");
        List<Group> groups = (List<Group>) groupDao.findAll();
        logger.trace("found {} groups.", groups.size());
        return groups.stream().map(g-> new GroupDto(g)).collect(Collectors.toList());
    }

    public GroupDto find(int id) {
        logger.debug("find() id {}.", id);
        Group group = groupDao.findById(id).get();
        logger.trace("found {}.", group);
        return new GroupDto(group);
    }

    public void add(GroupDto groupDto) {
        logger.debug("add() {}.", groupDto);
        Faculty faculty = facultyService.find(groupDto.getFacultyId());
        Group group = new Group(groupDto.getName(), faculty);
        groupDao.save(group);
    }

    @Transactional
    public void update(GroupDto groupDto) {
        logger.debug("update() {}.", groupDto);
        Faculty faculty = facultyService.find(groupDto.getFacultyId());
        Group group = new Group(groupDto.getId(), groupDto.getName(), faculty);
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
