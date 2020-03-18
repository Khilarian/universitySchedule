package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.List;

import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.exceptions.DaoException;
import com.rumakin.universityschedule.models.Faculty;

public class FacultyDao implements Dao<Faculty>, PreparedStatementBatchSetter<Faculty> {

    private static final String TABLE = "faculty";
    private static final String ID = "faculty_id";
    private static final String NAME = "faculty_name";

    private static final String ADD = "INSERT INTO " + TABLE + "(" + NAME + ")" + " VALUES " + "(?);";
    private static final String FIND_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + ID + "=?;";
    private static final String FIND_ALL = "SELECT * FROM " + TABLE + ";";

    private final JdbcTemplate jdbcTemplate;

    public FacultyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addAll(List<Faculty> data) {
        this.jdbcTemplate.batchUpdate(ADD, new BatchComposer<Faculty>(data, this));
    }

    @Override
    public void add(Faculty entity) {
        String facultyName = entity.getName();
        this.jdbcTemplate.update(ADD, facultyName);
    }

    @Override
    public Faculty findById(int id) {
        Faculty faculty = this.jdbcTemplate.queryForObject(FIND_BY_ID, new Object[] { id }, mapRow());
        if (faculty == null) {
            throw new DaoException("Faculty with id " + id + " is absent.");
        }
        return faculty;
    }

    @Override
    public List<Faculty> findAll() {
        List<Faculty> faculties = this.jdbcTemplate.query(FIND_ALL, mapRow());
        if (faculties.isEmpty()) {
            throw new DaoException("Faculty table is empty.");
        }
        return faculties;
    }

    @Override
    public void setStatements(PreparedStatement ps, Faculty faculty) throws SQLException {
        String facultyName = faculty.getName();
        ps.setString(1, facultyName);
    }

    @Override
    public RowMapper<Faculty> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Faculty(rs.getInt(ID), rs.getString(NAME));
    }
}
