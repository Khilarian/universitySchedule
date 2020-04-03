package com.rumakin.universityschedule.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;

import com.rumakin.universityschedule.exceptions.DaoException;
import com.rumakin.universityschedule.models.Auditorium;
import com.rumakin.universityschedule.models.Building;

class AuditoriumDaoTest {

    @Mock
    private JdbcTemplate mockJdbcTemplate;
    @Mock
    private BuildingDao mockBuildingDao;
    @Mock
    private Building mockBuilding;
    @Mock
    private Building mockBuildingTwo;

    private AuditoriumDao auditoriumDao = new AuditoriumDao(mockJdbcTemplate, mockBuildingDao);

    @Test
    void addShouldExecuteOnceWhenDbCallFine() throws SQLException {
        Auditorium auditorium = new Auditorium(1, 35, mockBuilding);
        when(mockBuilding.getId()).thenReturn(1);
        when(mockJdbcTemplate.queryForObject(anyString(), any(), Integer.class)).thenReturn(1);
        auditoriumDao.add(auditorium);
        verify(mockBuilding, times(1)).getId();
        verify(mockJdbcTemplate, times(1))
                .update(eq("INSERT INTO auditorium (number_id,capacity,building_id) values (?,?,?);"),
                        eq(1), eq(35), eq(1));
    }

//    @Test
//    void addAllShouldExecuteOnceWhenDbCallFine() throws SQLException {
//        Auditorium auditorium = new Auditorium(1, 35, mockBuilding);
//        Auditorium auditoriumTwo = new Auditorium(2, 15, mockBuildingTwo);
//        when(mockBuilding.getId()).thenReturn(1);
//        when(mockBuildingTwo.getId()).thenReturn(2);
//        Auditorium[] data = { auditorium, auditoriumTwo };
//        auditoriumDao.addAll(Arrays.asList(data));
//    }
////
//    @Test
//    void findShouldReturnPersonIfIdExists() throws SQLException {
//        Building expected = new Building("Main", "Moskow, Tverskaya, 1");
//        when(mockJdbcTemplate.queryForObject(any(String.class), (Object[]) any(Object.class),
//                (RowMapper<Building>) any(RowMapper.class))).thenReturn(expected);
//        Building actual = auditoriumDao.find(1);
//        assertEquals(expected, actual);
//        Object[] input = { 1 };
//        verify(mockJdbcTemplate, times(1)).queryForObject(eq("SELECT * FROM building WHERE building_id=?;"),
//                eq(input), any(RowMapper.class));
//    }
//
//    @Test
//    void findShouldRaiseExceptionIfIDMissed() throws SQLException {
//        when(mockJdbcTemplate.queryForObject(any(String.class), (Object[]) any(Object.class),
//                any(RowMapper.class))).thenThrow(DaoException.class);
//        assertThrows(DaoException.class, () -> auditoriumDao.find(20));
//        Object[] input = { 20 };
//        verify(mockJdbcTemplate, times(1)).queryForObject(eq("SELECT * FROM building WHERE building_id=?;"),
//                eq(input), any(RowMapper.class));
//    }
//
//    @Test
//    void findAllShouldReturnListOfCoursesIfAtLeastOneExists() throws SQLException {
//        Building building = new Building("Main", "Moskow, Tverskaya, 1");
//        Building buildingTwo = new Building("Second", "Khimki, Moskovskaya, 23");
//        List<Building> expected = Arrays.asList(building, buildingTwo);
//        when(mockJdbcTemplate.query(any(String.class), any(RowMapper.class))).thenReturn(expected);
//        List<Building> actual = auditoriumDao.findAll();
//        assertEquals(expected, actual);
//        verify(mockJdbcTemplate, times(1)).query(eq("SELECT * FROM building;"),
//                any(RowMapper.class));
//    }
//
//    @Test
//    void findAllShouldRaiseExceptionIfDataBaseEmpty() throws SQLException {
//        when(mockJdbcTemplate.query(any(String.class), any(RowMapper.class)))
//                .thenThrow(DaoException.class);
//        assertThrows(DaoException.class, () -> auditoriumDao.findAll());
//        verify(mockJdbcTemplate, times(1)).query(eq("SELECT * FROM building;"),
//                any(RowMapper.class));
//    }
//    
//    @Test
//    void removeShouldExecuteOnceWhenDbCallFine() throws SQLException {
//        auditoriumDao.remove(1);
//        verify(mockJdbcTemplate, times(1))
//                .update(eq("DELETE FROM building WHERE building_id =?;"),eq(1));
//    }

}
