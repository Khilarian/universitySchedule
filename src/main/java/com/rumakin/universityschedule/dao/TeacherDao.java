package com.rumakin.universityschedule.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.rumakin.universityschedule.models.Teacher;

public class TeacherDao extends Dao<Teacher> {
    
    private static final String TABLE = "teacher";
    private static final String ALIAS = "t";
    private static final String ID = "person_id";
    private static final String FACULTY_ID = "faculty_id";

    protected TeacherDao(JdbcTemplate jdbcTemplate) {
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
        return Arrays.asList(FACULTY_ID);
    }

    @Override
    protected RowMapper<Teacher> mapRow() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Object[] getFieldValues(Teacher entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getModelClassName() {
        // TODO Auto-generated method stub
        return null;
    }

}
