package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

import com.rumakin.universityschedule.models.*;

@Repository
public class AuditoriumDao implements Dao<Auditorium> {

    private static final String TABLE_NAME = "auditorium a";
    private static final String TABLE_ALIAS = "a";
    private static final String ID = "auditorium_id";
    private static final String NUMBER = "number_id";
    private static final String CAPACITY = "capacity";

    private static final String BUILDING_TABLE_NAME = "building b";
    private static final String BUILDING_TABLE_ALIAS = "b";
    private static final String BUILDING_ID = "building_id";
    private static final String BUILDING_NAME = "building_name";
    private static final String ADDRESS = "building_address";

    private static final String ADD = "INSERT INTO " + TABLE_NAME + " " + TABLE_ALIAS + " (" + NUMBER + "," + CAPACITY
            + "," + BUILDING_ID + ") values (?,?);";
    private static final String FIND_BY_ID = "SELECT " + TABLE_ALIAS + ID + " " + TABLE_ALIAS + NUMBER + " "
            + TABLE_ALIAS + CAPACITY + " " + BUILDING_TABLE_ALIAS + BUILDING_ID + " " + BUILDING_TABLE_ALIAS
            + BUILDING_NAME + " " + BUILDING_TABLE_ALIAS + ADDRESS + " FROM " + TABLE_NAME
            + " " + TABLE_ALIAS + " INNER JOIN " + BUILDING_TABLE_NAME + " " + BUILDING_TABLE_ALIAS
            + " ON " + TABLE_ALIAS + BUILDING_ID + "=" + BUILDING_TABLE_ALIAS + BUILDING_ID + " WHERE " + ID + "=?;";

    private static final String FIND_ALL = "SELECT * FROM " + TABLE_NAME + ";";
    private static final String REMOVE_BY_ID = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " =?;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuditoriumDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
                rs.getInt(ADDRESS),
                new Building(rs.getInt(BUILDING_ID), rs.getString(BUILDING_NAME), rs.getString(ADDRESS)));
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

}
