package com.rumakin.universityschedule.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.Faculty;

@Repository
public class FacultyDao extends Dao<Faculty> {

    @Autowired
    public FacultyDao() {
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
