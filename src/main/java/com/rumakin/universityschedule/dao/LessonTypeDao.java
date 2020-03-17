package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.List;

import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.dao.addbatch.LessonTypeAddBatch;
import com.rumakin.universityschedule.models.LessonType;

public class LessonTypeDao implements Dao<LessonType> {

    private static final String TABLE_NAME = "lesson_type";
    private static final String NAME = "lesson_type_name";

    private static final String ADD_LESSON_TYPE = "INSERT INTO " + TABLE_NAME + " (" + NAME + ") values (?);";
    private static final String FIND_BY_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + "=?;";
    private static final String FIND_ALL_LESSON_TYPE = "SELECT * FROM " + TABLE_NAME + ";";

    public final JdbcTemplate jdbcTemplate;

    public LessonTypeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addAll(List<LessonType> data) {
        this.jdbcTemplate.batchUpdate(ADD_LESSON_TYPE, new LessonTypeAddBatch(data));
    }

    @Override
    public void add(LessonType entity) {
        String lessonTypeName = entity.toString();
        this.jdbcTemplate.update(ADD_LESSON_TYPE, lessonTypeName);
    }

    public LessonType findByName(String name) {
        return this.jdbcTemplate.queryForObject(FIND_BY_NAME,
                new Object[] { name },
                new RowMapper<LessonType>() {
                    public LessonType mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return LessonType.valueOf(rs.getString(NAME));
                    }
                });
    }

    @Override
    public LessonType findById(int id) {
        return null;
    }

    @Override
    public List<LessonType> findAll() {
        return this.jdbcTemplate.query(FIND_ALL_LESSON_TYPE,
                new RowMapper<LessonType>() {
                    public LessonType mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return LessonType.valueOf(rs.getString(NAME));
                    }
                });
    }
}
