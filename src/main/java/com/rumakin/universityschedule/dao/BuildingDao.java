package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.models.Building;

public class BuildingDao implements Dao<Building>, ResultSetMapper<Building> {
    private static final String TABLE_NAME = "building";
    private static final String ID = "building_id";
    private static final String NAME = "building_name";
    private static final String ADDRESS = "building_address";

    private static final String ADD = "INSERT INTO " + TABLE_NAME + " (" + NAME + "," + ADDRESS
            + ") values (?,?);";
    private static final String FIND_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + "=?;";
    private static final String FIND_ALL = "SELECT * FROM " + TABLE_NAME + ";";

    public final JdbcTemplate jdbcTemplate;

    public BuildingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addAll(List<Building> data) {
        this.jdbcTemplate.batchUpdate(ADD, new BatchComposer<Building>(data, this));
    }

    @Override
    public void add(Building building) {
        String buildingName = building.getName();
        String buildingAddress = building.getAddress();
        this.jdbcTemplate.update(ADD, buildingName, buildingAddress);
    }

    @Override
    public Building findById(int id) {
        return this.jdbcTemplate.queryForObject(FIND_BY_ID, new Object[] { id }, mapRow());
    }

    @Override
    public List<Building> findAll() {
        return this.jdbcTemplate.query(FIND_ALL, mapRow());
    }

    @Override
    public RowMapper<Building> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Building(rs.getInt(ID), rs.getString(NAME),
                rs.getString(ADDRESS));
    }

    @Override
    public void setParameters(PreparedStatement ps, Building building) throws SQLException {
        String name = building.getName();
        String address = building.getAddress();
        ps.setString(1, name);
        ps.setString(2, address);

    }

}
