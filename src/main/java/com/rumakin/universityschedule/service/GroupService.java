package com.rumakin.universityschedule.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumakin.universityschedule.dao.GroupDao;
import com.rumakin.universityschedule.models.*;

@Service
public class GroupService {

    private GroupDao groupDao;
    
    @Autowired
    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }
    
    public List<Auditorium> findAuditoriumsForGroupOnDate(Group group, LocalDate date){
        int groupId = group.getId();
        return groupDao.findAuditoriumOnDate(groupId, date);
    }
}
