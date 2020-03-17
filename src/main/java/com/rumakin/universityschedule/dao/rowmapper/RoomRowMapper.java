package com.rumakin.universityschedule.dao.rowmapper;

import java.sql.*;

import org.springframework.jdbc.core.RowMapper;

import com.rumakin.universityschedule.models.Building;
import com.rumakin.universityschedule.models.Room;

public class RoomRowMapper implements RowMapper<Room> {

    private static final String ID = "room_id";
    private static final String NUMBER = "room_number";
    private static final String BUILDING_ID = "building_id";
    private static final String BUILDING_NAME = "building_name";
    private static final String BUILDING_ADDRESS = "building_address";

    public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt(ID);
        int number = rs.getInt(NUMBER);
        int buildingId = rs.getInt(BUILDING_ID);
        String buildingName = rs.getString(BUILDING_NAME);
        String buildingAddress = rs.getString(BUILDING_ADDRESS);
        Building building = new Building(buildingId, buildingName, buildingAddress);
        return new Room(id, number, building);
    }
}
