package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.rumakin.universityschedule.exceptions.DaoException;
import com.rumakin.universityschedule.models.Person;

public class PersonDao implements Dao<Person>, ResultSetMapper<Person> {

    private static final String TABLE_NAME = "person";
    private static final String ID = "person_id";
    private static final String FIRST_NAME = "person_first_name";
    private static final String LAST_NAME = "person_last_name";

    private static final String ADD = "INSERT INTO " + TABLE_NAME + " (" + FIRST_NAME + "," + LAST_NAME
            + ") values (?,?);";
    private static final String FIND_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + "=?;";
    private static final String FIND_ALL = "SELECT * FROM " + TABLE_NAME + ";";
    private static final String REMOVE_BY_ID = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " =?;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addAll(List<Person> data) {
        this.jdbcTemplate.batchUpdate(ADD, new BatchComposer<Person>(data, this));
    }

    @Override
    public void add(Person person) {
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        this.jdbcTemplate.update(ADD, firstName, lastName);
    }

    @SuppressWarnings("hiding")
    @Override
    public <Integer>Person find(Integer id) {
        Person person = this.jdbcTemplate.queryForObject(FIND_BY_ID, new Object[] { id }, mapRow());
        if (person == null) {
            throw new DaoException("Person with id " + id + " is absent.");
        }
        return person;
    }

    @Override
    public List<Person> findAll() {
        List<Person> people = this.jdbcTemplate.query(FIND_ALL, mapRow());
        if (people.isEmpty()) {
            throw new DaoException("Person table is empty.");
        }
        return people;
    }
    
    @SuppressWarnings("hiding")
    @Override
    public <Integer>void remove(Integer id) {
        this.jdbcTemplate.update(REMOVE_BY_ID, id);
    }

    @Override
    public RowMapper<Person> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Person(rs.getInt(ID), rs.getString(FIRST_NAME),
                rs.getString(LAST_NAME));
    }

    @Override
    public void setParameters(PreparedStatement ps, Person person) throws SQLException {
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        ps.setString(1, firstName);
        ps.setString(2, lastName);
    }

}
