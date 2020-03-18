package com.rumakin.universityschedule.dao.rowmapper;

import java.sql.*;

import org.springframework.jdbc.core.RowMapper;

import com.rumakin.universityschedule.enums.AcademicDegree;

public class AcademicDegreeRowMapper implements RowMapper<AcademicDegree> {

    private static final String NAME = "degree_name";

    public AcademicDegree mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AcademicDegree.valueOf(rs.getString(NAME));
    }
}
