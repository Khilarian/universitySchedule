package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.List;

import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.enums.CourseNumber;

public class CourseNumberDao implements Dao<CourseNumber>, PreparedStatementBatchSetter<CourseNumber> {
    private static final String TABLE_NAME = "course_number";
    private static final String NAME = "course_name";

    private static final String ADD_COURSE_NUMBER = "INSERT INTO " + TABLE_NAME + " (" + NAME + ") values (?);";
    private static final String FIND_BY_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + "=?;";
    private static final String FIND_ALL_COURSE_NUMBER = "SELECT * FROM " + TABLE_NAME + ";";

    public final JdbcTemplate jdbcTemplate;

    public CourseNumberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addAll(List<CourseNumber> data) {
        this.jdbcTemplate.batchUpdate(ADD_COURSE_NUMBER, new BatchComposer<CourseNumber>(data, this));
    }

    @Override
    public void add(CourseNumber courseNumber) {
        String courseName = courseNumber.name();
        this.jdbcTemplate.update(ADD_COURSE_NUMBER, courseName);
    }

    public CourseNumber findByName(String name) {
        return this.jdbcTemplate.queryForObject(FIND_BY_NAME, new Object[] { name }, mapRow());
    }

    @Override
    public CourseNumber findById(int id) {
        return null;
    }

    @Override
    public List<CourseNumber> findAll() {
        return this.jdbcTemplate.query(FIND_ALL_COURSE_NUMBER, mapRow());
    }

    @Override
    public void setStatements(PreparedStatement ps, CourseNumber courseNumber) throws SQLException {
        String courseNumberName = courseNumber.name();
        ps.setString(1, courseNumberName);
    }

    @Override
    public RowMapper<CourseNumber> mapRow() {
        return (ResultSet rs, int rowNumver) -> CourseNumber.valueOf(rs.getString(NAME));
    }

}
