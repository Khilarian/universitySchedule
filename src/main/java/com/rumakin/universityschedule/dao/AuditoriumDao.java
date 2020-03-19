package com.rumakin.universityschedule.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.rumakin.universityschedule.models.Auditorium;
import com.rumakin.universityschedule.models.Building;

public class AuditoriumDao implements Dao<Auditorium>, ResultSetMapper<Auditorium> {
    private static final String TABLE_NAME = "auditorium a";
    private static final String ID = "auditorium_id";
    private static final String NUMBER = "number_id";
    private static final String CAPACITY = "capacity";

    private static final String BUILDING_TABLE_NAME = "building b";
    private static final String BUILDING_ID = "building_id";
    private static final String BUILDING_NAME = "building_name";
    private static final String ADDRESS = "building_address";

    private static final String ADD = "INSERT INTO " + TABLE_NAME + " (" + NUMBER + "," + CAPACITY + "," + BUILDING_ID
            + ") values (?,?);";
    private static final String FIND_BY_ID = "SELECT a." + ID + " a." + NUMBER + " a." + CAPACITY + " b." + BUILDING_ID
            + " b." + BUILDING_NAME + " b." + ADDRESS + " FROM " + TABLE_NAME + " INNER JOIN " + BUILDING_TABLE_NAME
            + " ON a." + BUILDING_ID + "=" + "b." + BUILDING_ID
            + " WHERE " + ID + "=?;";

    private static final String FIND_ALL = "SELECT * FROM " + TABLE_NAME + ";";

    public final JdbcTemplate jdbcTemplate;

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

    @Override
    public Auditorium findById(int id) {
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
