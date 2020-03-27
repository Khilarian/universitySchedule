package com.rumakin.universityschedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dao.PersonDao;
import com.rumakin.universityschedule.models.Person;

public class PersonService {

private final PersonDao personDao;
    
    @Autowired
    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }
}
