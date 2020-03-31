package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.Faculty;

@Repository
public class FacultyDao implements Dao<Faculty> {

    private static final String TABLE = "faculty";
    private static final String ID = "faculty_id";
    private static final String NAME = "faculty_name";

    private static final String ADD = "INSERT INTO " + TABLE + " (" + NAME + ")" + " values "
            + "(?) RETURNING " + ID + ";";
    private static final String FIND_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + ID + "=?;";
    private static final String FIND_ALL = "SELECT * FROM " + TABLE + ";";
    private static final String REMOVE_BY_ID = "DELETE FROM " + TABLE + " WHERE " + ID + " =?;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FacultyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Faculty> addAll(List<Faculty> faculties) {
        for (Faculty faculty : faculties) {
            add(faculty);
        }
        return faculties;
    }

    @Override
    public Faculty add(Faculty faculty) {
        String facultyName = faculty.getName();
        int facultyId = this.jdbcTemplate.update(ADD, facultyName, Integer.class);
        faculty.setId(facultyId);
        return faculty;
    }

    @SuppressWarnings("hiding")
    @Override
    public <Integer> Faculty find(Integer id) {
        return this.jdbcTemplate.queryForObject(FIND_BY_ID, new Object[] { id }, mapRow());
    }

    @Override
    public List<Faculty> findAll() {
        return this.jdbcTemplate.query(FIND_ALL, mapRow());
    }

    @SuppressWarnings("hiding")
    @Override
    public <Integer> void remove(Integer id) {
        this.jdbcTemplate.update(REMOVE_BY_ID, id);
    }

    @Override
    public void setParameters(PreparedStatement ps, Faculty faculty) throws SQLException {
        String facultyName = faculty.getName();
        ps.setString(1, facultyName);
    }

    @Override
    public RowMapper<Faculty> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Faculty(rs.getInt(ID), rs.getString(NAME));
    }

    @Override
    public String getFieldsList(String alias) {
        List<String> fields = Arrays.asList(ID, NAME);
        return formatFieldsList(alias, fields);
    }

}