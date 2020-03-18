package com.rumakin.universityschedule.dao.rowmapper;

import java.sql.*;

import org.springframework.jdbc.core.RowMapper;

import com.rumakin.universityschedule.enums.LessonType;

public class LessonTypeRowMapper implements RowMapper<LessonType> {

    private static final String NAME = "lesson_type_name";

    public LessonType mapRow(ResultSet rs, int rowNum) throws SQLException {
        return LessonType.valueOf(rs.getString(NAME));
    }

}
