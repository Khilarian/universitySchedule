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
import com.rumakin.universityschedule.models.Faculty;

class FacultyDaoTest {

    private JdbcTemplate mockJdbcTemplate = mock(JdbcTemplate.class);

    private FacultyDao facultyDao = new FacultyDao(mockJdbcTemplate);

    @Test
    void addShouldExecuteOnceWhenDbCallFine() throws SQLException {
        Faculty faculty = new Faculty("Math");
        facultyDao.add(faculty);
        verify(mockJdbcTemplate, times(1)).update(eq("INSERT INTO faculty (faculty_name) values (?);"),
                        eq(faculty.getName()));
    }

    @Test
    void addAllShouldExecuteOnceWhenDbCallFine() throws SQLException {
        Faculty faculty = new Faculty("Math");
        Faculty facultyTwo = new Faculty("History");
        Faculty[] data = { faculty, facultyTwo };
        facultyDao.addAll(Arrays.asList(data));
        verify(mockJdbcTemplate, times(1)).batchUpdate(anyString(), any(BatchComposer.class));
    }

    @Test
    void findByIdShouldReturnFacultyIfIdExists() throws SQLException {
        Faculty expected = new Faculty("Math");
        when(mockJdbcTemplate.queryForObject(any(String.class), (Object[]) any(Object.class),
                (RowMapper<Faculty>) any(RowMapper.class))).thenReturn(expected);
        Faculty actual = facultyDao.find(1);
        assertEquals(expected, actual);
        Object[] input = { 1 };
        verify(mockJdbcTemplate, times(1)).queryForObject(eq("SELECT * FROM faculty WHERE faculty_id=?;"),
                eq(input), any(RowMapper.class));
    }

    @Test
    void findByIdShouldRaiseExceptionIfIDMissed() throws SQLException {
        when(mockJdbcTemplate.queryForObject(any(String.class), (Object[]) any(Object.class),
                any(RowMapper.class))).thenThrow(DaoException.class);
        assertThrows(DaoException.class, () -> facultyDao.find(20));
        Object[] input = { 20 };
        verify(mockJdbcTemplate, times(1)).queryForObject(eq("SELECT * FROM faculty WHERE faculty_id=?;"),
                eq(input), any(RowMapper.class));
    }

    @Test
    void findAllShouldReturnListOfCoursesIfAtLeastOneExist() throws SQLException {
        Faculty faculty = new Faculty("Math");
        Faculty facultyTwo = new Faculty("History");
        List<Faculty> expected = Arrays.asList(faculty, facultyTwo);
        when(mockJdbcTemplate.query(any(String.class), any(RowMapper.class))).thenReturn(expected);
        List<Faculty> actual = facultyDao.findAll();
        assertEquals(expected, actual);
        verify(mockJdbcTemplate, times(1)).query(eq("SELECT * FROM faculty;"),
                any(RowMapper.class));
    }

    @Test
    void findAllShouldRaiseExceptionIfDataBaseEmpty() throws SQLException {
        when(mockJdbcTemplate.query(any(String.class), any(RowMapper.class)))
                .thenThrow(DaoException.class);
        assertThrows(DaoException.class, () -> facultyDao.findAll());
        verify(mockJdbcTemplate, times(1)).query(eq("SELECT * FROM faculty;"),
                any(RowMapper.class));
    }

}
