package com.rumakin.universityschedule.dao.rowmapper;

import java.sql.*;

import org.springframework.jdbc.core.RowMapper;

import com.rumakin.universityschedule.enums.AcademicDegree;
import com.rumakin.universityschedule.models.Speciality;

public class SpecialityRowMapper implements RowMapper<Speciality> {

    private static final String ID = "speciality_id";
    private static final String NAME = "speciality_name";
    private static final String DEGREE = "academic_degree";

    public Speciality mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt(ID);
        String name = rs.getString(NAME);
        AcademicDegree degree = AcademicDegree.valueOf(rs.getString(DEGREE));
        return new Speciality(id, name, degree);
    }
}
