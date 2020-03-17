package com.rumakin.universityschedule.dao.rowmapper;

import java.sql.*;

import org.springframework.jdbc.core.RowMapper;

import com.rumakin.universityschedule.models.Building;

public class BuildingRowMapper implements RowMapper<Building> {

    private static final String ID = "building_id";
    private static final String NAME = "building_name";
    private static final String ADDRESS = "building_address";

    public Building mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt(ID);
        String name = rs.getString(NAME);
        String address = rs.getString(ADDRESS);
        return new Building(id, name, address);
    }
}
