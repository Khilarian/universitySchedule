package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.Building;

@Repository
public class BuildingDao implements Dao<Building> {

    private static final String TABLE = "building";
    private static final String ALIAS = "b";
    private static final String ID = "building_id";
    private static final String NAME = "building_name";
    private static final String ADDRESS = "building_address";

    private static final String ADD = "INSERT INTO " + TABLE + " (" + NAME + "," + ADDRESS + ") values (?,?);";
    private static final String FIND_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + ID + "=?;";
    private static final String FIND_ALL = "SELECT * FROM " + TABLE + ";";
    private static final String REMOVE_BY_ID = "DELETE FROM " + TABLE + " WHERE " + ID + " =?;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
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

    @SuppressWarnings("hiding")
    @Override
    public <Integer> Building find(Integer id) {
        return this.jdbcTemplate.queryForObject(FIND_BY_ID, new Object[] { id }, mapRow());
    }

    @Override
    public List<Building> findAll() {
        return this.jdbcTemplate.query(FIND_ALL, mapRow());
    }

    @SuppressWarnings("hiding")
    @Override
    public <Integer> void remove(Integer id) {
        this.jdbcTemplate.update(REMOVE_BY_ID, id);
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

    @Override
    public String getFieldsList() {
        return ALIAS + "." + ID + "," + ALIAS + "." + NAME + "," + ALIAS + "." + ADDRESS;
    }

    @Override
    public String getTableName() {
        return TABLE;
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }

}
