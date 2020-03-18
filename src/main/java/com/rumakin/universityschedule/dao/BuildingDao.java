package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.models.Building;

public class BuildingDao implements Dao<Building>, PreparedStatementBatchSetter<Building> {
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
        this.jdbcTemplate.batchUpdate(ADD_BUILDING, new BatchComposer<Building>(data, this));
    }

    @Override
    public void add(Building building) {
        String buildingName = building.getName();
        String buildingAddress = building.getAddress();
        this.jdbcTemplate.update(ADD_BUILDING, buildingName, buildingAddress);
    }

    public int findIdByName(String name) {
        return this.jdbcTemplate.queryForObject(FIND_ID_BY_NAME, Integer.class, name);
    }

    @Override
    public Building findById(int id) {
        return this.jdbcTemplate.queryForObject(FIND_BUILDING_BY_ID, new Object[] { id }, mapRow());
    }

    @Override
    public List<Building> findAll() {
        return this.jdbcTemplate.query(FIND_ALL_BUILDING, mapRow());
    }

    @Override
    public void setStatements(PreparedStatement ps, Building building) throws SQLException {
        String name = building.getName();
        String address = building.getAddress();
        ps.setString(1, name);
        ps.setString(2, address);

    }

    @Override
    public RowMapper<Building> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Building(rs.getInt(ID), rs.getString(NAME),
                rs.getString(ADDRESS));
    }
}
