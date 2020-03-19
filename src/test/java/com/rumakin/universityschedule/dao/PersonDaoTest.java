package com.rumakin.universityschedule.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.exceptions.DaoException;
import com.rumakin.universityschedule.models.Person;

class PersonDaoTest {

    private JdbcTemplate mockJdbcTemplate = mock(JdbcTemplate.class);

    private PersonDao personDao = new PersonDao(mockJdbcTemplate);

    @Test
    void addShouldExecuteOnceWhenDbCallFine() throws SQLException {
        Person person = new Person("Lexx", "Luger");
        personDao.add(person);
        verify(mockJdbcTemplate, times(1))
                .update(eq("INSERT INTO person (person_first_name,person_last_name) values (?,?);"),
                        eq(person.getFirstName()), eq(person.getLastName()));
    }

    @Test
    void addAllShouldExecuteOnceWhenDbCallFine() throws SQLException {
        Person person = new Person("Lexx", "Luger");
        Person personTwo = new Person("Hulk", "Hogan");
        Person[] data = { person, personTwo };
        personDao.addAll(Arrays.asList(data));
        verify(mockJdbcTemplate, times(1)).batchUpdate(anyString(), any(BatchComposer.class));
    }

    @Test
    void findByIdShouldReturnPersonIfIdExists() throws SQLException {
        Person expected = new Person(1, "Lexx", "Luger");
        when(mockJdbcTemplate.queryForObject(any(String.class), (Object[]) any(Object.class),
                (RowMapper<Person>) any(RowMapper.class))).thenReturn(expected);
        Person actual = personDao.find(1);
        assertEquals(expected, actual);
        Object[] input = { 1 };
        verify(mockJdbcTemplate, times(1)).queryForObject(eq("SELECT * FROM person WHERE person_id=?;"),
                eq(input), any(RowMapper.class));
    }

    @Test
    void findByIdShouldRaiseExceptionIfIDMissed() throws SQLException {
        when(mockJdbcTemplate.queryForObject(any(String.class), (Object[]) any(Object.class),
                any(RowMapper.class))).thenThrow(DaoException.class);
        assertThrows(DaoException.class, () -> personDao.find(20));
        Object[] input = { 20 };
        verify(mockJdbcTemplate, times(1)).queryForObject(eq("SELECT * FROM person WHERE person_id=?;"),
                eq(input), any(RowMapper.class));
    }

    @Test
    void findAllShouldReturnListOfCoursesIfAtLeastOneExist() throws SQLException {
        Person person = new Person("Lexx", "Luger");
        Person personTwo = new Person("Hulk", "Hogan");
        List<Person> expected = Arrays.asList(person, personTwo);
        when(mockJdbcTemplate.query(any(String.class), any(RowMapper.class))).thenReturn(expected);
        List<Person> actual = personDao.findAll();
        assertEquals(expected, actual);
        verify(mockJdbcTemplate, times(1)).query(eq("SELECT * FROM person;"),
                any(RowMapper.class));
    }

    @Test
    void findAllShouldRaiseExceptionIfDataBaseEmpty() throws SQLException {
        when(mockJdbcTemplate.query(any(String.class), any(RowMapper.class)))
                .thenThrow(DaoException.class);
        assertThrows(DaoException.class, () -> personDao.findAll());
        verify(mockJdbcTemplate, times(1)).query(eq("SELECT * FROM person;"),
                any(RowMapper.class));
    }

}
