package com.rumakin.universityschedule.dao;

import javax.persistence.*;

import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.Teacher;

@Repository
public class TeacherDao extends Dao<Teacher> {

    protected TeacherDao(EntityManager entityManager) {
        super(entityManager);
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
