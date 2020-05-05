package com.rumakin.universityschedule.dao;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.Course;

@Repository
public class SubjectDao extends Dao<Course> {

    @Autowired
    public SubjectDao(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    protected String getModelClassName() {
        return Course.class.getSimpleName();
    }

    @Override
    protected Class<Course> getEntityClass() {
        return Course.class;
    }

}
