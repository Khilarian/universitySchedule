package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.Faculty;

@Repository
public class FacultyDao extends Dao<Faculty> {

    private static final String TABLE = "faculty";
    private static final String ALIAS = "f";
    private static final String ID = "faculty_id";
    private static final String NAME = "faculty_name";

    @Autowired
    public FacultyDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public RowMapper<Faculty> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Faculty(rs.getInt(ID), rs.getString(NAME));
    }

    @Override
    String getTableAlias() {
        return ALIAS;
    }

    @Override
    String getTableName() {
        return TABLE;
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
    Object[] getFieldValues(Faculty faculty) {
        return new Object[] { faculty.getName() };
    }

}
