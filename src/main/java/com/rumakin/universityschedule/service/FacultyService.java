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
    
    public void add(Faculty faculty) {
        facultyDao.add(faculty);
    }
    
    public void addAll(List<Faculty> facultys) {
        facultyDao.addAll(facultys);
    }
    
    public Faculty find(int id) {
        return facultyDao.find(id);
    }
    
    public List<Faculty> findAll(){
        return facultyDao.findAll();
    }
    
    public void remove(int id) {
        facultyDao.remove(id);
    }
}
