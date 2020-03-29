package com.rumakin.universityschedule.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.models.Subject;

public class SubjectDao implements Dao<Subject> {

    private static final String TABLE = "subject";
    private static final String ID = "subject_id";
    private static final String NAME = "subject_name";
    private static final String FACULTY_ID = "faculty_id";

    private static final String ADD = "INSERT INTO " + TABLE + " (" + NAME + "," + FACULTY_ID
            + ") values (?,?) RETURNING " + ID + ";";
    private static final String FIND_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + ID + "=?;";
    private static final String FIND_ALL = "SELECT * FROM " + TABLE + ";";
    private static final String REMOVE_BY_ID = "DELETE FROM " + TABLE + " WHERE " + ID + " =?;";

    private JdbcTemplate jdbcTemplate;
    private FacultyDao facultyDao;

    @Autowired
    public SubjectDao(JdbcTemplate jdbcTemplate, FacultyDao facultyDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.facultyDao = facultyDao;
    }

    @Override
    public List<Subject> addAll(List<Subject> subjects) {
        for (Subject subject : subjects) {
            add(subject);
        }
        return subjects;
    }

    @Override
    public Subject add(Subject subject) {
        String subjectName = subject.getName();
        int facultyId = subject.getFaculty().getId();
        Object[] input = { subjectName, facultyId };
        int subjectId = this.jdbcTemplate.update(ADD, input, Integer.class);
        subject.setId(subjectId);
        return subject;
    }

    @SuppressWarnings("hiding")
    @Override
    public <Integer> Subject find(Integer id) {
        return this.jdbcTemplate.queryForObject(FIND_BY_ID, new Object[] { id }, mapRow());
    }

    @Override
    public List<Subject> findAll() {
        return this.jdbcTemplate.query(FIND_ALL, mapRow());
    }

    @Override
    public RowMapper<Subject> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Subject(rs.getInt(ID), rs.getString(NAME),
                facultyDao.find(rs.getInt(FACULTY_ID)));
    }

    @SuppressWarnings("hiding")
    @Override
    public <Integer> void remove(Integer id) {
        this.jdbcTemplate.update(REMOVE_BY_ID, id);
    }

    @Override
    public void setParameters(PreparedStatement ps, Subject subject) throws SQLException {
        String name = subject.getName();
        int buildingId = subject.getFaculty().getId();
        ps.setString(1, name);
        ps.setInt(2, buildingId);
    }

    @Override
    public String getFieldsList(String alias) {
        List<String> fields = Arrays.asList(ID, NAME, FACULTY_ID);
        return formatFieldsList(alias, fields);
    }

}
