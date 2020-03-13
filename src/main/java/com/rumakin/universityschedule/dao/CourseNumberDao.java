package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.List;

import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.models.CourseNumber;

public class CourseNumberDao implements Dao<CourseNumber> {
    private static final String TABLE_NAME = "course_number";
    private static final String ID = "course_id";
    private static final String NAME = "course_name";

    private static final String ADD_COURSE_NUMBER = "INSERT INTO " + TABLE_NAME
            + " (" + ID + "," + NAME + ") values (?,?);";
    private static final String FIND_COURSE_NUMBER_BY_ID = "SELECT " + NAME + " FROM "
            + TABLE_NAME + " WHERE " + ID + "=?;";
    private static final String FIND_ALL_COURSE_NUMBER = "SELECT " + NAME + " FROM "
            + TABLE_NAME + ";";

    public final JdbcTemplate jdbcTemplate;

    public CourseNumberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class CourseNumberAddBatch implements BatchPreparedStatementSetter {

        private final List<CourseNumber> courses;

        public CourseNumberAddBatch(final List<CourseNumber> data) {
            this.courses = data;
        }

        public final void setValues(
                final PreparedStatement ps,
                final int i) throws SQLException {
            ps.setInt(1, courses.get(i).getIndex());
            ps.setString(2, courses.get(i).name());
        }

        @Override
        public int getBatchSize() {
            return courses.size();
        }
    }

    @Override
    public void addAll(List<CourseNumber> data) {
        this.jdbcTemplate.batchUpdate(ADD_COURSE_NUMBER, new CourseNumberAddBatch(data));
    }

    @Override
    public void add(CourseNumber entity) {
        int courseId = entity.getIndex();
        String courseName = entity.name();
        this.jdbcTemplate.update(ADD_COURSE_NUMBER, courseId, courseName);
    }

    @Override
    public CourseNumber findById(int id) {
        return this.jdbcTemplate.queryForObject(FIND_COURSE_NUMBER_BY_ID,
                new Object[] { 1212L },
                new RowMapper<CourseNumber>() {
                    public CourseNumber mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return CourseNumber.valueOf(rs.getString(NAME));
                    }
                });
    }

    @Override
    public List<CourseNumber> findAll() {
        return this.jdbcTemplate.query(FIND_ALL_COURSE_NUMBER,
                new RowMapper<CourseNumber>() {
                    public CourseNumber mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return CourseNumber.valueOf(rs.getString(NAME));
                    }
                });
    }

}
