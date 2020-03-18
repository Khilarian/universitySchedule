package com.rumakin.universityschedule.dao;
import java.util.List;

import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.dao.addbatch.CourseNumberAddBatch;
import com.rumakin.universityschedule.dao.rowmapper.CourseNumberRowMapper;
import com.rumakin.universityschedule.enums.CourseNumber;

public class CourseNumberDao implements Dao<CourseNumber> {
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
        this.jdbcTemplate.batchUpdate(ADD_COURSE_NUMBER, new CourseNumberAddBatch(data));
    }

    @Override
    public void add(CourseNumber entity) {
        String courseName = entity.name();
        this.jdbcTemplate.update(ADD_COURSE_NUMBER, courseName);
    }

    public CourseNumber findByName(String name) {
        return this.jdbcTemplate.queryForObject(FIND_BY_NAME, new Object[] { name }, new CourseNumberRowMapper());
    }

    @Override
    public CourseNumber findById(int id) {
        return null;
    }

    @Override
    public List<CourseNumber> findAll() {
        return this.jdbcTemplate.query(FIND_ALL_COURSE_NUMBER, new CourseNumberRowMapper());
    }

}
