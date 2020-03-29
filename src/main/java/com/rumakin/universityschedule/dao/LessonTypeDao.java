package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.enums.LessonType;

@Repository
public class LessonTypeDao implements Dao<LessonType> {

    private static final String TABLE = "lesson_type";
    private static final String NAME = "lesson_type_name";

    private static final String ADD = "INSERT INTO " + TABLE + " (" + NAME + ") values (?);";
    private static final String FIND_BY_NAME = "SELECT * FROM " + TABLE + " WHERE " + NAME + "=?;";
    private static final String FIND_ALL = "SELECT * FROM " + TABLE + ";";
    private static final String REMOVE_BY_NAME = "DELETE FROM " + TABLE + " WHERE " + NAME + " =?;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LessonTypeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<LessonType> addAll(List<LessonType> types) {
        for (LessonType type: types) {
            add(type);
        }
        return types;
    }

    @Override
    public LessonType add(LessonType lessonType) {
        String lessonTypeName = lessonType.toString();
        this.jdbcTemplate.update(ADD, lessonTypeName);
        return lessonType;
    }

    @SuppressWarnings("hiding")
    @Override
    public <String> LessonType find(String name) {
        return this.jdbcTemplate.queryForObject(FIND_BY_NAME, new Object[] { name }, mapRow());
    }

    @Override
    public List<LessonType> findAll() {
        return this.jdbcTemplate.query(FIND_ALL, mapRow());
    }

    @SuppressWarnings("hiding")
    @Override
    public <String> void remove(String name) {
        this.jdbcTemplate.update(REMOVE_BY_NAME, name);
    }

    @Override
    public void setParameters(PreparedStatement ps, LessonType lessonType) throws SQLException {
        String lessonTypeName = lessonType.name();
        ps.setString(1, lessonTypeName);
    }

    @Override
    public RowMapper<LessonType> mapRow() {
        return (ResultSet rs, int rowNumver) -> LessonType.valueOf(rs.getString(NAME));
    }

    @Override
    public String getFieldsList(String alias) {
        List<String> fields = Arrays.asList(NAME);
        return formatFieldsList(alias, fields);
    }

}
