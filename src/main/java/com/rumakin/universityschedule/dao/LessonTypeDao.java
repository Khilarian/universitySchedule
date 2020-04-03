package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.enums.LessonType;

@Repository
public class LessonTypeDao extends Dao<LessonType> {

    private static final String TABLE = "lesson_type";
    private static final String ALIAS = "lt";
    private static final String ID = "lesson_type_id";
    private static final String NAME = "lesson_type_name";

    @Autowired
    public LessonTypeDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    java.lang.String getTableName() {
        return TABLE;
    }

    @Override
    java.lang.String getTableAlias() {
        return ALIAS;
    }

    @Override
    String getEntityIdName() {
        return ID;
    }

    @Override
    List<String> getFieldsNames() {
        return Arrays.asList(NAME);
    }

    @Override
    public RowMapper<LessonType> mapRow() {
        return (ResultSet rs, int rowNumver) -> LessonType.valueOf(rs.getString(NAME));
    }

    @Override
    Object[] getFieldValues(LessonType entity) {
        return new Object[] { NAME };
    }

}
