package com.rumakin.universityschedule.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.rumakin.universityschedule.dao.AuditoriumDao;
import com.rumakin.universityschedule.models.Auditorium;

@Service
public class AuditoriumService {

    @Autowired
    private AuditoriumDao auditoriumDao;

    public AuditoriumService(AuditoriumDao auditoriumDao) {
        this.auditoriumDao = auditoriumDao;
    }
    
    public void add(Auditorium auditorium) {
        auditoriumDao.add(auditorium);
    }
    
    public void addAll(List<Auditorium> auditoriums) {
        auditoriumDao.addAll(auditoriums);
    }
    
    public Auditorium find(int id) {
        return auditoriumDao.find(id);
    }
    
    public List<Auditorium> findAll(){
        return auditoriumDao.findAll();
    }
    
    public void remove(int id) {
        auditoriumDao.remove(id);
    }
}
