package com.rumakin.universityschedule.dao;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.Person;

@Repository
public class PersonDao extends Dao<Person> {

    @Autowired
    public PersonDao(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    protected String getModelClassName() {
        return Person.class.getSimpleName();
    }

    @Override
    protected Class<Person> getEntityClass() {
        return Person.class;
    }
}
