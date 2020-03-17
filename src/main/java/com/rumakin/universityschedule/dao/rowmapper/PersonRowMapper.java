package com.rumakin.universityschedule.dao.rowmapper;

import java.sql.*;

import org.springframework.jdbc.core.RowMapper;

import com.rumakin.universityschedule.models.Person;

public class PersonRowMapper implements RowMapper<Person> {

    private static final String ID = "person_id";
    private static final String FIRST_NAME = "person_first_name";
    private static final String LAST_NAME = "person_last_name";

    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt(ID);
        String firstName = rs.getString(FIRST_NAME);
        String lastName = rs.getString(LAST_NAME);
        return new Person(id, firstName, lastName);
    }
}
