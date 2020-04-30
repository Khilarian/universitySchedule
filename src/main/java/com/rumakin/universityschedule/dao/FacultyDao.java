package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.hibernate.SessionFactory;
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
    public FacultyDao(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        super(jdbcTemplate, sessionFactory);
    }

    @Override
    protected String getTableAlias() {
        return ALIAS;
    }

    @Override
    protected String getTableName() {
        return TABLE;
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
    public RowMapper<Faculty> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Faculty(rs.getInt(ID), rs.getString(NAME));
    }

    @Override
    protected Object[] getFieldValues(Faculty faculty) {
        return new Object[] { faculty.getName() };
    }

    @Override
    protected String getModelClassName() {
        return Faculty.class.getSimpleName();
    }

}
