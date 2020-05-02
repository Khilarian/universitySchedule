package com.rumakin.universityschedule.dao;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.*;

@Repository
public class GroupDao extends Dao<Group> {

    @Autowired
    public GroupDao(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    protected String getModelClassName() {
        return Group.class.getSimpleName();
    }

    @Override
    protected Class<Group> getEntityClass() {
        return Group.class;
    }

}
