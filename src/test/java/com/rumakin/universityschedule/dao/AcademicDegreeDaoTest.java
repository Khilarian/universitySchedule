package com.rumakin.universityschedule.dao;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.dao.addbatch.AcademicDegreeAddBatch;
import com.rumakin.universityschedule.dao.rowmapper.AcademicDegreeRowMapper;
import com.rumakin.universityschedule.enums.AcademicDegree;
import com.rumakin.universityschedule.exceptions.DaoException;

class AcademicDegreeDaoTest {

    private JdbcTemplate mockJdbcTemplate = mock(JdbcTemplate.class);

    private AcademicDegreeDao academicDegreeDao = new AcademicDegreeDao(mockJdbcTemplate);

    @Test
    void addShouldExecuteOnceWhenDbCallFine() throws SQLException {
        AcademicDegree degree = AcademicDegree.BACHELOR;
        academicDegreeDao.add(degree);
        verify(mockJdbcTemplate, times(1)).update(eq("INSERT INTO academic_degree (degree_name) values (?);"),
                eq("BACHELOR"));
    }

    @Test
    void addAllShouldExecuteOnceWhenDbCallFine() throws SQLException {
        AcademicDegree degree = AcademicDegree.BACHELOR;
        AcademicDegree degreeTwo = AcademicDegree.CANDIDAT_OF_SCIENCES;
        AcademicDegree[] data = { degree, degreeTwo };
        academicDegreeDao.addAll(Arrays.asList(data));
        verify(mockJdbcTemplate, times(1)).batchUpdate(anyString(), any(AcademicDegreeAddBatch.class));
    }

    @Test
    void findByNameShouldReturnAcademicDegreeIfNameExists() throws SQLException {
        AcademicDegree expected = AcademicDegree.MASTER;
        when(mockJdbcTemplate.queryForObject(any(String.class), (Object[]) any(Object.class),
                any(AcademicDegreeRowMapper.class)))
                        .thenReturn(AcademicDegree.MASTER);
        AcademicDegree actual = academicDegreeDao.findByName("MASTER");
        assertEquals(expected, actual);
        Object[] input = { "MASTER" };
        verify(mockJdbcTemplate, times(1)).queryForObject(eq("SELECT * FROM academic_degree WHERE degree_name=?;"),
                eq(input), any(AcademicDegreeRowMapper.class));
    }

    @Test
    void findByIdShouldRaiseExceptionIfIDMissed() throws SQLException {
        when(mockJdbcTemplate.queryForObject(any(String.class), (Object[]) any(Object.class),
                any(AcademicDegreeRowMapper.class)))
                        .thenThrow(DaoException.class);
        assertThrows(DaoException.class, () -> academicDegreeDao.findByName("BACHELOR"));
        Object[] input = { "BACHELOR" };
        verify(mockJdbcTemplate, times(1)).queryForObject(eq("SELECT * FROM academic_degree WHERE degree_name=?;"),
                eq(input), any(AcademicDegreeRowMapper.class));
    }

    @Test
    void findAllShouldReturnListOfCoursesIfAtLeastOneExist() throws SQLException {
        List<AcademicDegree> expected = Arrays.asList(AcademicDegree.BACHELOR, AcademicDegree.MASTER);
        when(mockJdbcTemplate.query(any(String.class), any(AcademicDegreeRowMapper.class))).thenReturn(expected);
        List<AcademicDegree> actual = academicDegreeDao.findAll();
        assertEquals(expected, actual);
        verify(mockJdbcTemplate, times(1)).query(eq("SELECT * FROM academic_degree;"),
                any(AcademicDegreeRowMapper.class));
    }

    @Test
    void findAllShouldRaiseExceptionIfDataBaseEmpty() throws SQLException {
        when(mockJdbcTemplate.query(any(String.class), any(AcademicDegreeRowMapper.class)))
                .thenThrow(DaoException.class);
        assertThrows(DaoException.class, () -> academicDegreeDao.findAll());
        verify(mockJdbcTemplate, times(1)).query(eq("SELECT * FROM academic_degree;"),
                any(AcademicDegreeRowMapper.class));
    }
}
