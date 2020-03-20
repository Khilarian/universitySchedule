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
    
    public void add(Person person) {
        personDao.add(person);
    }
    
    public void addAll(List<Person> persons) {
        personDao.addAll(persons);
    }
    
    public Person find(int id) {
        return personDao.find(id);
    }
    
    public List<Person> findAll(){
        return personDao.findAll();
    }
    
    public void remove(int id) {
        personDao.remove(id);
    }
}
