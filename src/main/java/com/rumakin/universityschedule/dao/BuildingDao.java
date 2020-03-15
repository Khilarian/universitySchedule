package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.List;

import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.dao.addbatch.BuildingAddBatch;
import com.rumakin.universityschedule.models.Building;

public class BuildingDao implements Dao<Building> {
    private static final String TABLE_NAME = "building";
    private static final String ID = "building_id";
    private static final String NAME = "building_name";
    private static final String ADDRESS = "building_address";

    private static final String ADD_BUILDING = "INSERT INTO " + TABLE_NAME + " (" + NAME + "," + ADDRESS
            + ") values (?,?);";
    private static final String FIND_BUILDING_BY_ID = "SELECT " + NAME + "," + ADDRESS + " FROM "
            + TABLE_NAME + " WHERE " + ID + "=?;";
    private static final String FIND_ID_BY_NAME = "SELECT " + ID + " FROM " + TABLE_NAME + " WHERE " + NAME + "=?;";
    private static final String FIND_ALL_BUILDING = "SELECT " + NAME + "," + ADDRESS + " FROM " + TABLE_NAME + ";";

    public final JdbcTemplate jdbcTemplate;

    public BuildingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addAll(List<Building> data) {
        this.jdbcTemplate.batchUpdate(ADD_BUILDING, new BuildingAddBatch(data));
    }

    @Override
    public void add(Building entity) {
        String buildingName = entity.getName();
        String buildingAddress = entity.getAddress();
        this.jdbcTemplate.update(ADD_BUILDING, buildingName, buildingAddress);
    }

    public int findIdByName(String name) {
        return this.jdbcTemplate.queryForObject(FIND_ID_BY_NAME, Integer.class, name);
    }

    @Override
    public Building findById(int id) {
        return this.jdbcTemplate.queryForObject(FIND_BUILDING_BY_ID,
                new Object[] { id },
                new RowMapper<Building>() {
                    public Building mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new Building(rs.getInt(ID), rs.getString(NAME), rs.getString(ADDRESS));
                    }
                });
    }

    @Override
    public List<Building> findAll() {
        return this.jdbcTemplate.query(FIND_ALL_BUILDING,
                new RowMapper<Building>() {
                    public Building mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new Building(rs.getInt(ID), rs.getString(NAME), rs.getString(ADDRESS));
                    }
                });
    }
}
