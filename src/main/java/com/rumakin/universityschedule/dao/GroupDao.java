package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.*;

@Repository
public class GroupDao extends Dao<Group> {

    private static final String TABLE = "groups";
    private static final String ALIAS = "g";
    private static final String ID = "group_id";
    private static final String NAME = "group_name";
    private static final String FACULTY_ID = "faculty_id";

    private final FacultyDao facultyDao;

    @Autowired
    public GroupDao(JdbcTemplate jdbcTemplate, FacultyDao facultyDao) {
        super(jdbcTemplate);
        this.facultyDao = facultyDao;
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
        return Arrays.asList(NAME, FACULTY_ID);
    }

    @Override
    public RowMapper<Group> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Group(rs.getInt(ID), rs.getString(NAME),
                facultyDao.find(rs.getInt(FACULTY_ID)));
    }

    @Override
    protected Object[] getFieldValues(Group group) {
        return new Object[] { group.getName(), group.getFaculty().getId() };
    }

    @Override
    protected String getModelClassName() {
        return Group.class.getSimpleName();
    }

}
