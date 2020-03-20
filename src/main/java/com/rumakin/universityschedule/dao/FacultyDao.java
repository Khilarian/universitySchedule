package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.exceptions.DaoException;
import com.rumakin.universityschedule.models.Faculty;

@Repository
public class FacultyDao implements Dao<Faculty>, ResultSetMapper<Faculty> {

    private static final String TABLE_NAME = "faculty";
    private static final String ID = "faculty_id";
    private static final String NAME = "faculty_name";

    private static final String ADD = "INSERT INTO " + TABLE_NAME + " (" + NAME + ")" + " values " + "(?);";
    private static final String FIND_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + "=?;";
    private static final String FIND_ALL = "SELECT * FROM " + TABLE_NAME + ";";
    private static final String REMOVE_BY_ID = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " =?;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
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

    @SuppressWarnings("hiding")
    @Override
    public <Integer>Faculty find(Integer id) {
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
    
    @SuppressWarnings("hiding")
    @Override
    public <Integer>void remove(Integer id) {
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
}
