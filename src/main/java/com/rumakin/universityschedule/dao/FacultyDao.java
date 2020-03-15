package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.List;

import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.dao.addbatch.FacultyAddBatch;
import com.rumakin.universityschedule.dao.addbatch.RoomAddBatch;
import com.rumakin.universityschedule.models.Building;
import com.rumakin.universityschedule.models.Faculty;
import com.rumakin.universityschedule.models.Room;

public class FacultyDao implements Dao<Faculty> {

    private static final String TABLE = "faculty";
    private static final String ID = "faculty_id";
    private static final String NAME = "faculty_name";
    private static final String OFFICE = "office_id";

    private static final String ADD = "INSERT INTO " + TABLE + "(" + NAME + "," + OFFICE + ")" + " VALUES " + "(?,?);";
    private static final String FIND_ID_BY_NAME = "SELECT * FROM " + TABLE + " WHERE " + NAME + "=?;";
    private static final String FIND_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + ID + "=?;";
    
    
    
    private static final String FIND_ALL = "SELECT * FROM " + TABLE + ";";

    private final JdbcTemplate jdbcTemplate;

    public FacultyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addAll(List<Faculty> data) {
        this.jdbcTemplate.batchUpdate(ADD, new FacultyAddBatch(data));
    }

    @Override
    public void add(Faculty entity) {
        String facultyName = entity.getName();
        int officeId = entity.getOffice().getId();
        this.jdbcTemplate.update(ADD, facultyName, officeId);
    }

    public int findIdByName(String name) {
        return this.jdbcTemplate.queryForObject(FIND_ID_BY_NAME, Integer.class, name);
    }

    @Override
    public Faculty findById(int id) {
        return this.jdbcTemplate.queryForObject(FIND_BY_ID,
                new Object[] { id },
                new RowMapper<Faculty>() {
                    public Faculty mapRow(ResultSet rs, int rowNum) throws SQLException {
                        String name = rs.getString(NAME);
                        int officeId = rs.getInt(OFFICE);
                        String buildingName = rs.getString(BUILDING_NAME);
                        String buildingAddress = rs.getString(BUILDING_ADDRESS);
                       Office office = new Office();
                        return new Faculty(id, name, office);
                    }
                });
    }

    @Override
    public List<Faculty> findAll() {
        return this.jdbcTemplate.query(FIND_ALL,
                new RowMapper<Faculty>() {
                    public Faculty mapRow(ResultSet rs, int rowNum) throws SQLException {
                        int id = rs.getInt(ID);
                        int number = rs.getInt(NUMBER);
                        int floor = rs.getInt(FLOOR);
                        int buildingId = rs.getInt(BUILDING_ID);
                        String buildingName = rs.getString(BUILDING_NAME);
                        String buildingAddress = rs.getString(BUILDING_ADDRESS);
                        Building building = new Building(buildingId, buildingName, buildingAddress);
                        return new Faculty(id, number, floor, building);
                    }
                });
    }
}
