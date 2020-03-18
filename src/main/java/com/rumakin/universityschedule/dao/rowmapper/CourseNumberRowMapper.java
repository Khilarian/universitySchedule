package com.rumakin.universityschedule.dao.rowmapper;

import java.sql.*;

import org.springframework.jdbc.core.RowMapper;

import com.rumakin.universityschedule.enums.CourseNumber;

public class CourseNumberRowMapper implements RowMapper<CourseNumber> {

    private static final String NAME = "course_name";

    public CourseNumber mapRow(ResultSet rs, int rowNum) throws SQLException {
        return CourseNumber.valueOf(rs.getString(NAME));
    }
}
