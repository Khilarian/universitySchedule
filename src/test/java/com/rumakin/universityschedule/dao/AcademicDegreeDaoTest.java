package com.rumakin.universityschedule.dao;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.rumakin.universityschedule.dao.addbatch.AcademicDegreeAddBatch;
import com.rumakin.universityschedule.dao.rowmapper.AcademicDegreeRowMapper;
import com.rumakin.universityschedule.exceptions.DaoException;
import com.rumakin.universityschedule.models.AcademicDegree;

class AcademicDegreeDaoTest {

    JdbcTemplate mockJdbcTemplate = mock(JdbcTemplate.class);
    AcademicDegreeRowMapper mockAcademicDegreeRowMapper = mock(AcademicDegreeRowMapper.class);

    private AcademicDegreeDao academicDegreeDao = new AcademicDegreeDao(mockJdbcTemplate);

    @Test
    void addShouldExecuteOnceWhenDbCallFine() throws SQLException {
        AcademicDegree degree = AcademicDegree.BACHELOR;
        academicDegreeDao.add(degree);
        verify(mockJdbcTemplate, times(1)).update(anyString(), anyString());
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
        when(mockJdbcTemplate.queryForObject(eq(anyString()), any(), new AcademicDegreeRowMapper())).thenReturn(AcademicDegree.MASTER);
        when(mockAcademicDegreeRowMapper.mapRow(any(), anyInt())).thenReturn(AcademicDegree.MASTER);
        AcademicDegree actual = academicDegreeDao.findByName("MASTER");
        assertEquals(expected, actual);
        verify(mockJdbcTemplate, times(1)).queryForObject(eq(anyString()), any(), new AcademicDegreeRowMapper());
    }

    @Test
    void findByIdShouldRaiseExceptionIfIDMissed() throws SQLException {
        when(mockJdbcTemplate.queryForObject(eq(anyString()), any(), new AcademicDegreeRowMapper()))
                .thenThrow(DaoException.class);
        assertThrows(DaoException.class, () -> academicDegreeDao.findByName("name"));
    }

    /*
     * @Test void findAllShouldReturnListOfCoursesIfAtLeastOneExist() throws
     * SQLException {
     * 
     * }
     */

}
