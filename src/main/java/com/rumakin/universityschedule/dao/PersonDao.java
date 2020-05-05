package com.rumakin.universityschedule.dao;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.Person;

@Repository
public class PersonDao extends Dao<Person> {

    @Autowired
    public PersonDao(EntityManager entityManager) {
        super(entityManager);
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
