package com.rumakin.universityschedule.dao;

import java.util.List;

import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.dao.addbatch.RoomAddBatch;
import com.rumakin.universityschedule.dao.rowmapper.RoomRowMapper;
import com.rumakin.universityschedule.models.Room;

public class RoomDao implements Dao<Room> {
    private static final String TABLE_NAME = "room r";
    private static final String ID = "room_id";
    private static final String NUMBER = "room_number";
    private static final String BUILDING_TABLE_NAME = "building b";
    private static final String BUILDING_ID = "building_id";
    private static final String BUILDING_NAME = "building_name";
    private static final String BUILDING_ADDRESS = "building_address";

    private static final String ADD_ROOM = "INSERT INTO " + TABLE_NAME
            + " (" + NUMBER + "," + BUILDING_ID + ") values (?,?);";
    private static final String FIND_ROOM_BY_ID = "SELECT (r." + ID + ",r." + NUMBER
            + ",r." + BUILDING_ID + ",b." + BUILDING_NAME + ",b." + BUILDING_ADDRESS + ") FROM "
            + TABLE_NAME + " INNER JOIN " + BUILDING_TABLE_NAME + " ON r." + BUILDING_ID + "=b." + BUILDING_ID
            + " WHERE " + ID + "=?;";
    private static final String FIND_ID_BY_NUMBER_AND_BUILDING = "SELECT " + ID + " FROM " + TABLE_NAME
            + " WHERE " + NUMBER + "=?  AND " + BUILDING_ID + "=?;";
    private static final String FIND_ALL_ROOM = "SELECT (r." + ID + ",r." + NUMBER
            + ",r." + BUILDING_ID + ",b." + BUILDING_NAME + ",b." + BUILDING_ADDRESS + ") FROM " + TABLE_NAME
            + " INNER JOIN " + BUILDING_TABLE_NAME + " ON r." + BUILDING_ID + "=b." + BUILDING_ID;

    private final JdbcTemplate jdbcTemplate;

    public RoomDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addAll(List<Room> data) {
        this.jdbcTemplate.batchUpdate(ADD_ROOM, new RoomAddBatch(data));
    }

    @Override
    public void add(Room entity) {
        int roomNumber = entity.getNumber();
        int buildingId = entity.getBuilding().getId();
        this.jdbcTemplate.update(ADD_ROOM, roomNumber, buildingId);
    }

    public int findIdByNumberFloorBuilding(int number, int floor, int buildingId) {
        return this.jdbcTemplate.queryForObject(FIND_ID_BY_NUMBER_AND_BUILDING, Integer.class, number, floor,
                buildingId);
    }

    @Override
    public Room findById(int id) {
        return this.jdbcTemplate.queryForObject(FIND_ROOM_BY_ID, new Object[] { id }, new RoomRowMapper());
    }

    @Override
    public List<Room> findAll() {
        return this.jdbcTemplate.query(FIND_ALL_ROOM, new RoomRowMapper());
    }
}
