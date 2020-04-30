package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.Building;

@Repository
public class BuildingDao extends Dao<Building> {

    private static final String TABLE = "building";
    private static final String ALIAS = "b";
    private static final String ID = "building_id";
    private static final String NAME = "building_name";
    private static final String ADDRESS = "building_address";

    @Autowired
    public BuildingDao(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        super(jdbcTemplate, sessionFactory);
    }

    @Override
    protected String getTableName() {
        return TABLE;
    }

    @Override
    protected String getTableAlias() {
        return ALIAS;
    }

    @Override
    protected String getEntityIdName() {
        return ID;
    }

    @Override
    protected List<String> getFieldsNames() {
        return Arrays.asList(NAME, ADDRESS);
    }

    @Override
    public RowMapper<Building> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Building(rs.getInt(ID), rs.getString(NAME), rs.getString(ADDRESS));
    }

    @Override
    protected Object[] getFieldValues(Building building) {
        return new Object[] { building.getName(), building.getAddress() };
    }

    @Override
    protected String getModelClassName() {
        return Building.class.getSimpleName();
    }

}
