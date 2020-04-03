package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.models.Subject;

public class SubjectDao extends Dao<Subject> {

    private static final String TABLE = "subject";
    private static final String ALIAS = "su";
    private static final String ID = "subject_id";
    private static final String NAME = "subject_name";
    private static final String FACULTY_ID = "faculty_id";

    private FacultyDao facultyDao;

    @Autowired
    public SubjectDao(JdbcTemplate jdbcTemplate, FacultyDao facultyDao) {
        super(jdbcTemplate);
        this.facultyDao = facultyDao;
    }

    @Override
    String getTableName() {
        return TABLE;
    }

    @Override
    String getTableAlias() {
        return ALIAS;
    }

    @Override
    String getEntityIdName() {
        return ID;
    }

    @Override
    List<String> getFieldsNames() {
        return Arrays.asList(ID, NAME, FACULTY_ID);
    }

    @Override
    public RowMapper<Subject> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Subject(rs.getInt(ID), rs.getString(NAME),
                facultyDao.find(rs.getInt(FACULTY_ID)));
    }

    @Override
    Object[] getFieldValues(Subject subject) {
        return new Object[] { subject.getName(), subject.getFaculty() };
    }

}
