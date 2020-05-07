package com.rumakin.universityschedule.dao;

import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.Teacher;

@Repository
public class TeacherDao extends Dao<Teacher> {

    protected TeacherDao() {
    }

    @Override
    protected String getModelClassName() {
        return Teacher.class.getSimpleName();
    }

    @Override
    protected Class<Teacher> getEntityClass() {
        return Teacher.class;
    }

}
