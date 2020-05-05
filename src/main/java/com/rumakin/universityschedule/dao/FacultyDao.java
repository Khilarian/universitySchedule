package com.rumakin.universityschedule.dao;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.Faculty;

@Repository
public class FacultyDao extends Dao<Faculty> {

    @Autowired
    public FacultyDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected String getModelClassName() {
        return Faculty.class.getSimpleName();
    }

    @Override
    protected Class<Faculty> getEntityClass() {
        return Faculty.class;
    }

}
