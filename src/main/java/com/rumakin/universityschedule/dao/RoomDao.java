package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.List;

import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.models.Building;
import com.rumakin.universityschedule.models.Room;

public class RoomDao implements Dao<Room> {
    private static final String TABLE_NAME = "room";
    private static final String ID = "room_id";
    private static final String NUMBER = "room_number";
    private static final String FLOOR = "floor_number";
    private static final String BUILDING_ID = "building_id";

    private static final String ADD_ROOM = "INSERT INTO " + TABLE_NAME
            + " (" + NUMBER + "," + FLOOR + "," + BUILDING_ID + ") values (?,?,?);";
    private static final String FIND_ROOM_BY_ID = "SELECT " + NUMBER + "," + FLOOR + "," + BUILDING_ID + " FROM "
            + TABLE_NAME + " WHERE " + ID + "=?;";
    private static final String FIND_ID_BY_NUMBER_FLOOR_AND_BUILDING = "SELECT " + ID + " FROM " + TABLE_NAME
            + " WHERE "
            + NUMBER + "=? AND " + FLOOR + "=? AND " + BUILDING_ID + "=?;";
    private static final String FIND_ALL_ROOM = "SELECT " + NUMBER + "," + FLOOR + "," + BUILDING_ID + " FROM "
            + TABLE_NAME + ";";

    private final JdbcTemplate jdbcTemplate;
    private final BuildingDao buildingDao;

    public RoomDao(JdbcTemplate jdbcTemplate, BuildingDao buildingDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.buildingDao = buildingDao;
    }

    private static class RoomAddBatch implements BatchPreparedStatementSetter {

        private final List<Room> rooms;

        public RoomAddBatch(final List<Room> data) {
            this.rooms = data;
        }

        public final void setValues(
                final PreparedStatement ps,
                final int i) throws SQLException {
            ps.setInt(1, rooms.get(i).getNumber());
            ps.setInt(2, rooms.get(i).getFloor());
            ps.setInt(3, rooms.get(i).getBuilding().getId());
        }

        @Override
        public int getBatchSize() {
            return rooms.size();
        }
    }

    @Override
    public void addAll(List<Room> data) {
        this.jdbcTemplate.batchUpdate(ADD_ROOM, new RoomAddBatch(data));
    }

    @Override
    public void add(Room entity) {
        int roomNumber = entity.getNumber();
        int roomFloor = entity.getFloor();
        int buildingId = entity.getBuilding().getId();
        this.jdbcTemplate.update(ADD_ROOM, roomNumber, roomFloor, buildingId);
    }
    
    public int findByNumberFloorBuilding(int number,int floor, int buildingId) {
        return this.jdbcTemplate.queryForObject(FIND_ID_BY_NUMBER_FLOOR_AND_BUILDING, Integer.class, number,floor,buildingId);
    }

    @Override
    public Room findById(int id) {
        return this.jdbcTemplate.queryForObject(FIND_ROOM_BY_ID,
                new Object[] { 1212L },
                new RowMapper<Room>() {
                    public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Building building = buildingDao.findById(rs.getInt(BUILDING_ID));
                        return new Room(rs.getInt(NUMBER), rs.getInt(FLOOR), building);
                    }
                });
    }

    @Override
    public List<Room> findAll() {
        return this.jdbcTemplate.query(FIND_ALL_ROOM,
                new RowMapper<Room>() {
                    public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Building building = buildingDao.findById(rs.getInt(BUILDING_ID));
                        return new Room(rs.getInt(NUMBER), rs.getInt(FLOOR), building);
                    }
                });
    }
}
