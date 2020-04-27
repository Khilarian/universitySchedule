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
    protected String getTableName() {
        return TABLE;
    }

    @Override
    protected String getTableAlias() {
        return ALIAS;
    }

    @Override
    protected String getEntityIdName() {
        return ID;
    }

    @Override
    protected List<String> getFieldsNames() {
        return Arrays.asList(NAME);
    }

    @Override
    public RowMapper<LessonType> mapRow() {
        return (ResultSet rs, int rowNumver) -> LessonType.valueOf(rs.getString(NAME));
    }

    @Override
    protected Object[] getFieldValues(LessonType entity) {
        return new Object[] { NAME };
    }

    @Override
    protected String getModelClassName() {
        return LessonType.class.getSimpleName();
    }

}
