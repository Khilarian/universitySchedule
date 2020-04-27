package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.Person;

@Repository
public class PersonDao extends Dao<Person> {

    private static final String TABLE = "person";
    private static final String ALIAS = "p";
    private static final String ID = "person_id";
    private static final String FIRST_NAME = "person_first_name";
    private static final String LAST_NAME = "person_last_name";

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
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
    public RowMapper<Person> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Person(rs.getInt(ID), rs.getString(FIRST_NAME),
                rs.getString(LAST_NAME));
    }

    @Override
    protected List<String> getFieldsNames() {
        return Arrays.asList(FIRST_NAME, LAST_NAME);
    }

    @Override
    protected Object[] getFieldValues(Person person) {
        return new Object[] { person.getFirstName(), person.getLastName() };
    }

    @Override
    protected String getModelClassName() {
        return Person.class.getSimpleName();
    }
}
