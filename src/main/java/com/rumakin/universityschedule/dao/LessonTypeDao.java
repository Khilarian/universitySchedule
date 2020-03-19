package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.List;

import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.enums.LessonType;
import com.rumakin.universityschedule.exceptions.DaoException;

public class LessonTypeDao implements Dao<LessonType>, StatementFiller<LessonType> {

    private static final String TABLE_NAME = "lesson_type";
    private static final String NAME = "lesson_type_name";

    private static final String ADD = "INSERT INTO " + TABLE_NAME + " (" + NAME + ") values (?);";
    private static final String FIND_BY_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + "=?;";
    private static final String FIND_ALL = "SELECT * FROM " + TABLE_NAME + ";";

    public final JdbcTemplate jdbcTemplate;

    public LessonTypeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addAll(List<LessonType> data) {
        this.jdbcTemplate.batchUpdate(ADD, new BatchComposer<LessonType>(data, this));
    }

    @Override
    public void add(LessonType lessonType) {
        String lessonTypeName = lessonType.toString();
        this.jdbcTemplate.update(ADD, lessonTypeName);
    }

    public LessonType findByName(String name) {
        LessonType degree = this.jdbcTemplate.queryForObject(FIND_BY_NAME, new Object[] { name },
                mapRow());
        if (degree == null) {
            throw new DaoException("LessonType with name " + name + " is absent.");
        }
        return degree;
    }

    @Override
    public LessonType findById(int id) {
        return null;
    }

    @Override
    public List<LessonType> findAll() {
        List<LessonType> types = this.jdbcTemplate.query(FIND_ALL, mapRow());
        if (types.isEmpty()) {
            throw new DaoException("LessonType table is empty.");
        }
        return types;
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
}
