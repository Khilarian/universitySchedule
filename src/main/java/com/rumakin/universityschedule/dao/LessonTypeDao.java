package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.List;

import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.models.LessonType;

public class LessonTypeDao implements Dao<LessonType> {
    private static final String TABLE_NAME = "lesson_type";
    private static final String ID = "lesson_type_id";
    private static final String NAME = "lesson_type_name";

    private static final String ADD_LESSON_TYPE = "INSERT INTO " + TABLE_NAME
            + " (" + NAME + ") values (?);";
    private static final String FIND_LESSON_TYPE_BY_ID = "SELECT " + NAME + " FROM "
            + TABLE_NAME + " WHERE " + ID + "=?;";
    private static final String FIND_ALL_LESSON_TYPE = "SELECT " + NAME + " FROM "
            + TABLE_NAME + ";";

    public final JdbcTemplate jdbcTemplate;

    public LessonTypeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class LessonTypeAddBatch implements BatchPreparedStatementSetter {

        private final List<LessonType> lessonTypes;

        public LessonTypeAddBatch(final List<LessonType> data) {
            this.lessonTypes = data;
        }

        public final void setValues(
                final PreparedStatement ps,
                final int i) throws SQLException {
            ps.setString(1, lessonTypes.get(i).toString());
        }

        @Override
        public int getBatchSize() {
            return lessonTypes.size();
        }
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

    @Override
    public LessonType findById(int id) {
        return this.jdbcTemplate.queryForObject(FIND_LESSON_TYPE_BY_ID,
                new Object[] { 1212L },
                new RowMapper<LessonType>() {
                    public LessonType mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return LessonType.valueOf(rs.getString(NAME));
                    }
                });
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
