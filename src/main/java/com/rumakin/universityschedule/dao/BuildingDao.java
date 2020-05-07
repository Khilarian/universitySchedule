package com.rumakin.universityschedule.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.Building;

@Repository
public class BuildingDao extends Dao<Building> {

    @Autowired
    public BuildingDao() {
    }

    @Override
    protected String getModelClassName() {
        return Building.class.getSimpleName();
    }

    @Override
    protected Class<Building> getEntityClass() {
        return Building.class;
    }

}
