package com.rumakin.universityschedule.dao;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.enums.LessonType;

@Repository
public class LessonTypeDao extends Dao<LessonType> {

    @Autowired
    public LessonTypeDao(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected String getModelClassName() {
        return LessonType.class.getSimpleName();
    }

    @Override
    protected Class<LessonType> getEntityClass() {
        return LessonType.class;
    }

}
