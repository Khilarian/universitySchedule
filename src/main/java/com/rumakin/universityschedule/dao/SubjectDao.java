package com.rumakin.universityschedule.dao;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.Subject;

@Repository
public class SubjectDao extends Dao<Subject> {

    @Autowired
    public SubjectDao(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    protected String getModelClassName() {
        return Subject.class.getSimpleName();
    }

    @Override
    protected Class<Subject> getEntityClass() {
        return Subject.class;
    }

}
