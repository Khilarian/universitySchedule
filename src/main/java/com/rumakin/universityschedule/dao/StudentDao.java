package com.rumakin.universityschedule.dao;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.*;

@Repository
public class StudentDao extends Dao<Student> {

    @Autowired
    public StudentDao(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    protected String getModelClassName() {
        return Student.class.getSimpleName();
    }

    @Override
    protected Class<Student> getEntityClass() {
        return Student.class;
    }

}
