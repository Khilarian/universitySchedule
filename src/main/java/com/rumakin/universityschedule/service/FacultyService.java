package com.rumakin.universityschedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dao.FacultyDao;
import com.rumakin.universityschedule.models.Faculty;

public class FacultyService {

private final FacultyDao facultyDao;
    
    @Autowired
    public FacultyService(FacultyDao facultyDao) {
        this.facultyDao = facultyDao;
    }
}
