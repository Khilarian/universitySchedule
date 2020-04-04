package com.rumakin.universityschedule.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.*;
import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.exceptions.DaoException;
import com.rumakin.universityschedule.models.Person;

class PersonDaoTest {

    @Mock
    private JdbcTemplate mockJdbcTemplate;
    @InjectMocks
    private PersonDao personDao = new PersonDao(mockJdbcTemplate);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addShouldExecuteOnceWhenDbCallFine() throws SQLException {
        when(mockJdbcTemplate.queryForObject(anyString(), any(), eq(Integer.class))).thenReturn(1);
        Person person = new Person("Lexx", "Luger");
        Person expected = new Person(1, "Lexx", "Luger");
        Person actual = personDao.add(person);
        assertEquals(expected, actual);
        verify(mockJdbcTemplate, times(1))
                .queryForObject(eq(
                        "INSERT INTO person p (p.person_first_name,p.person_last_name) VALUES (?,?) RETURNING person_id;"),
                        eq(new Object[] { "Lexx", "Luger" }), eq(Integer.class));
    }

    @Test
    void addAllShouldExecuteOnceWhenDbCallFine() throws SQLException {
        when(mockJdbcTemplate.queryForObject(anyString(), any(), eq(Integer.class))).thenReturn(1, 2);
        Person person = new Person("Lexx", "Luger");
        Person personTwo = new Person("Hulk", "Hogan");
        Person[] data = { person, personTwo };
        List<Person> expected = Arrays.asList(new Person(1, "Lexx", "Luger"), new Person(2, "Hulk", "Hogan"));
        List<Person> actual = personDao.addAll(Arrays.asList(data));
        verify(mockJdbcTemplate, times(1))
        .queryForObject(eq(
                "INSERT INTO person p (p.person_first_name,p.person_last_name) VALUES (?,?) RETURNING person_id;"),
                eq(new Object[] { "Lexx", "Luger" }), eq(Integer.class));
        verify(mockJdbcTemplate, times(1))
        .queryForObject(eq(
                "INSERT INTO person p (p.person_first_name,p.person_last_name) VALUES (?,?) RETURNING person_id;"),
                eq(new Object[] { "Hulk", "Hogan" }), eq(Integer.class));
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
    void findAllShouldReturnListOfCoursesIfAtLeastOneExists() throws SQLException {
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
    void removeShouldExecuteOnceWhenDbCallFine() throws SQLException {
        personDao.remove(1);
        verify(mockJdbcTemplate, times(1))
                .update(eq("DELETE FROM person WHERE person_id=?;"), eq(1));
    }

}
