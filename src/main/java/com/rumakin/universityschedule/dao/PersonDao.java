package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.Person;

@Repository
public class PersonDao implements Dao<Person> {

    private static final String TABLE = "person";
    private static final String ID = "person_id";
    private static final String FIRST_NAME = "person_first_name";
    private static final String LAST_NAME = "person_last_name";

    private static final String ADD = "INSERT INTO " + TABLE + " (" + FIRST_NAME + "," + LAST_NAME
            + ") values (?,?) RETURNING " + ID + ";";
    private static final String FIND_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + ID + "=?;";
    private static final String FIND_ALL = "SELECT * FROM " + TABLE + ";";
    private static final String REMOVE_BY_ID = "DELETE FROM " + TABLE + " WHERE " + ID + " =?;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Person> addAll(List<Person> people) {
        for (Person person: people) {
            add(person);
        }
        return people;
    }

    @Override
    public Person add(Person person) {
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        Object[] input = { firstName, lastName };
        int personId = this.jdbcTemplate.queryForObject(ADD, input, Integer.class);
        person.setId(personId);
        return person;
    }

    @SuppressWarnings("hiding")
    @Override
    public <Integer> Person find(Integer id) {
        return this.jdbcTemplate.queryForObject(FIND_BY_ID, new Object[] { id }, mapRow());
    }

    @Override
    public List<Person> findAll() {
        return this.jdbcTemplate.query(FIND_ALL, mapRow());
    }

    @SuppressWarnings("hiding")
    @Override
    public <Integer> void remove(Integer id) {
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

    @Override
    public String getFieldsList(String alias) {
        List<String> fields = Arrays.asList(ID, FIRST_NAME, LAST_NAME);
        return formatFieldsList(alias, fields);
    }

}
