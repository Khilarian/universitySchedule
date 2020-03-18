package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.rumakin.universityschedule.exceptions.DaoException;
import com.rumakin.universityschedule.models.Person;

public class PersonDao implements Dao<Person>, PreparedStatementBatchSetter<Person> {

    private static final String TABLE_NAME = "person";
    private static final String ID = "person_id";
    private static final String FIRST_NAME = "person_first_name";
    private static final String LAST_NAME = "person_last_name";

    private static final String ADD = "INSERT INTO " + TABLE_NAME + " (" + FIRST_NAME + "," + LAST_NAME
            + ") values (?,?);";
    private static final String FIND_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + "=?;";
    private static final String FIND_BY_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE " + FIRST_NAME + "=? AND "
            + LAST_NAME + "=;";
    private static final String FIND_ALL = "SELECT * FROM " + TABLE_NAME + ";";

    public final JdbcTemplate jdbcTemplate;

    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addAll(List<Person> data) {
        this.jdbcTemplate.batchUpdate(ADD, new BatchComposer<Person>(data, this));
    }

    @Override
    public void add(Person person) {
        this.jdbcTemplate.update(ADD, person);
    }

    @Override
    public Person findById(int id) {
        Person person = this.jdbcTemplate.queryForObject(FIND_BY_ID, new Object[] { id }, mapRow());
        if (person == null) {
            throw new DaoException("Person with id " + id + " is absent.");
        }
        return person;
    }

    public Person findByName(String firstName, String lastName) {
        Person person = this.jdbcTemplate.queryForObject(FIND_BY_NAME, new Object[] { firstName, lastName },
                mapRow());
        if (person == null) {
            throw new DaoException("Person with name " + firstName + " " + lastName + " is absent.");
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

    @Override
    public RowMapper<Person> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Person(rs.getInt(ID), rs.getString(FIRST_NAME),
                rs.getString(LAST_NAME));

    }

    @Override
    public void setStatements(PreparedStatement ps, Person person) throws SQLException {
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        ps.setString(1, firstName);
        ps.setString(2, lastName);
    }

}
