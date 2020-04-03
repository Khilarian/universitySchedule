package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

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
    public BuildingDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    String getTableName() {
        return TABLE;
    }

    @Override
    String getTableAlias() {
        return ALIAS;
    }

    @Override
    String getEntityIdName() {
        return ID;
    }

    @Override
    List<String> getFieldsNames() {
        return Arrays.asList(NAME, ADDRESS);
    }

    @Override
    public RowMapper<Building> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Building(rs.getInt(ID), rs.getString(NAME),
                rs.getString(ADDRESS));
    }

    @Override
    Object[] getFieldValues(Building building) {
        return new Object[] { building.getName(), building.getAddress() };
    }

}
