package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.enums.AcademicDegree;
import com.rumakin.universityschedule.exceptions.DaoException;
import com.rumakin.universityschedule.models.Speciality;

public class SpecialityDao implements Dao<Speciality>, PreparedStatementBatchSetter<Speciality> {

    private static final String TABLE_NAME = "speciality";
    private static final String ID = "speciality_id";
    private static final String NAME = "speciality_name";
    private static final String DEGREE = "degree_name";

    private static final String ADD = "INSERT INTO " + TABLE_NAME + " (" + NAME + "," + DEGREE
            + ") values (?,?);";
    private static final String FIND_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + "=?;";
    private static final String FIND_BY_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + "=?;";
    private static final String FIND_ALL = "SELECT * FROM " + TABLE_NAME + ";";

    public final JdbcTemplate jdbcTemplate;

    public SpecialityDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addAll(List<Speciality> data) {
        this.jdbcTemplate.batchUpdate(ADD, new BatchComposer<Speciality>(data, this));
    }

    @Override
    public void add(Speciality speciality) {
        this.jdbcTemplate.update(ADD, speciality);
    }

    @Override
    public Speciality findById(int id) {
        Speciality speciality = this.jdbcTemplate.queryForObject(FIND_BY_ID, new Object[] { id },
                mapRow());
        if (speciality == null) {
            throw new DaoException("Speciality with id " + id + " is absent.");
        }
        return speciality;
    }

    public Speciality findByName(String name) {
        Speciality speciality = this.jdbcTemplate.queryForObject(FIND_BY_NAME, new Object[] { name },
                mapRow());
        if (speciality == null) {
            throw new DaoException("Speciality with name " + name + " is absent.");
        }
        return speciality;
    }

    @Override
    public List<Speciality> findAll() {
        List<Speciality> specialities = this.jdbcTemplate.query(FIND_ALL, mapRow());
        if (specialities.isEmpty()) {
            throw new DaoException("Speciality table is empty.");
        }
        return specialities;
    }

    @Override
    public void setStatements(PreparedStatement ps, Speciality speciality) throws SQLException {
        String name = speciality.getName();
        String degree = speciality.getAcademicDegree().name();
        ps.setString(1, name);
        ps.setString(2, degree);

    }

    @Override
    public RowMapper<Speciality> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Speciality(rs.getInt(ID), rs.getString(NAME),
                AcademicDegree.valueOf(rs.getString(DEGREE)));
    }

}
