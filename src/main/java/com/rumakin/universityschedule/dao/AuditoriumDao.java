package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

import com.rumakin.universityschedule.models.*;

@Repository
public class AuditoriumDao implements Dao<Auditorium> {

    private static final String TABLE = "auditorium";
    private static final String ID = "auditorium_id";
    private static final String NUMBER = "number_id";
    private static final String CAPACITY = "capacity";
    private static final String BUILDING_ID = "building_id";

    private static final String ADD = "INSERT INTO " + TABLE + " (" + NUMBER + "," + CAPACITY
            + "," + BUILDING_ID + ") values (?,?,?);";
    private static final String FIND_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + ID + "=?;";

    private static final String FIND_ALL = "SELECT * FROM " + TABLE + ";";
    private static final String REMOVE_BY_ID = "DELETE FROM " + TABLE + " WHERE " + ID + " =?;";

    private final JdbcTemplate jdbcTemplate;
    private final BuildingDao buildingDao;

    @Autowired
    public AuditoriumDao(JdbcTemplate jdbcTemplate, BuildingDao buildingDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.buildingDao = buildingDao;
    }

    @Override
    public void addAll(List<Auditorium> data) {
        this.jdbcTemplate.batchUpdate(ADD, new BatchComposer<Auditorium>(data, this));
    }

    @Override
    public void add(Auditorium auditorium) {
        int auditoriumNumber = auditorium.getNumber();
        int auditoriumCapacity = auditorium.getCapacity();
        int buildingID = auditorium.getBuilding().getId();
        this.jdbcTemplate.update(ADD, auditoriumNumber, auditoriumCapacity, buildingID);
    }

    @SuppressWarnings("hiding")
    @Override
    public <Integer> Auditorium find(Integer id) {
        return this.jdbcTemplate.queryForObject(FIND_BY_ID, new Object[] { id }, mapRow());
    }

    @Override
    public List<Auditorium> findAll() {
        return this.jdbcTemplate.query(FIND_ALL, mapRow());
    }

    @Override
    public RowMapper<Auditorium> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Auditorium(rs.getInt(ID), rs.getInt(NUMBER),
                rs.getInt(CAPACITY), buildingDao.find(rs.getInt(BUILDING_ID)));
    }

    @SuppressWarnings("hiding")
    @Override
    public <Integer> void remove(Integer id) {
        this.jdbcTemplate.update(REMOVE_BY_ID, id);
    }

    @Override
    public void setParameters(PreparedStatement ps, Auditorium auditorium) throws SQLException {
        int number = auditorium.getNumber();
        int capacity = auditorium.getCapacity();
        int buildingId = auditorium.getBuilding().getId();
        ps.setInt(1, number);
        ps.setInt(2, capacity);
        ps.setInt(3, buildingId);
    }

    @Override
    public String getFieldsList(String alias) {
        List<String> fields = Arrays.asList(ID, NUMBER, CAPACITY, BUILDING_ID);
        return formatFieldsList(alias, fields);
    }

}
