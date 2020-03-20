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
import com.rumakin.universityschedule.models.Building;

class BuildingDaoTest {

    private JdbcTemplate mockJdbcTemplate = mock(JdbcTemplate.class);

    private BuildingDao buildingDao = new BuildingDao(mockJdbcTemplate);

    @Test
    void addShouldExecuteOnceWhenDbCallFine() throws SQLException {
        Building building = new Building("Main", "Moskow, Tverskaya, 1");
        buildingDao.add(building);
        verify(mockJdbcTemplate, times(1))
                .update(eq("INSERT INTO building (building_name,building_address) values (?,?);"),
                        eq(building.getName()), eq(building.getAddress()));
    }

    @Test
    void addAllShouldExecuteOnceWhenDbCallFine() throws SQLException {
        Building building = new Building("Main", "Moskow, Tverskaya, 1");
        Building buildingTwo = new Building("Second", "Khimki, Moskovskaya, 23");
        Building[] data = { building, buildingTwo };
        buildingDao.addAll(Arrays.asList(data));
        verify(mockJdbcTemplate, times(1)).batchUpdate(anyString(), any(BatchComposer.class));
    }

    @Test
    void findShouldReturnPersonIfIdExists() throws SQLException {
        Building expected = new Building("Main", "Moskow, Tverskaya, 1");
        when(mockJdbcTemplate.queryForObject(any(String.class), (Object[]) any(Object.class),
                (RowMapper<Building>) any(RowMapper.class))).thenReturn(expected);
        Building actual = buildingDao.find(1);
        assertEquals(expected, actual);
        Object[] input = { 1 };
        verify(mockJdbcTemplate, times(1)).queryForObject(eq("SELECT * FROM building WHERE building_id=?;"),
                eq(input), any(RowMapper.class));
    }

    @Test
    void findShouldRaiseExceptionIfIDMissed() throws SQLException {
        when(mockJdbcTemplate.queryForObject(any(String.class), (Object[]) any(Object.class),
                any(RowMapper.class))).thenThrow(DaoException.class);
        assertThrows(DaoException.class, () -> buildingDao.find(20));
        Object[] input = { 20 };
        verify(mockJdbcTemplate, times(1)).queryForObject(eq("SELECT * FROM building WHERE building_id=?;"),
                eq(input), any(RowMapper.class));
    }

    @Test
    void findAllShouldReturnListOfCoursesIfAtLeastOneExists() throws SQLException {
        Building building = new Building("Main", "Moskow, Tverskaya, 1");
        Building buildingTwo = new Building("Second", "Khimki, Moskovskaya, 23");
        List<Building> expected = Arrays.asList(building, buildingTwo);
        when(mockJdbcTemplate.query(any(String.class), any(RowMapper.class))).thenReturn(expected);
        List<Building> actual = buildingDao.findAll();
        assertEquals(expected, actual);
        verify(mockJdbcTemplate, times(1)).query(eq("SELECT * FROM building;"),
                any(RowMapper.class));
    }

    @Test
    void findAllShouldRaiseExceptionIfDataBaseEmpty() throws SQLException {
        when(mockJdbcTemplate.query(any(String.class), any(RowMapper.class)))
                .thenThrow(DaoException.class);
        assertThrows(DaoException.class, () -> buildingDao.findAll());
        verify(mockJdbcTemplate, times(1)).query(eq("SELECT * FROM building;"),
                any(RowMapper.class));
    }
    
    @Test
    void removeShouldExecuteOnceWhenDbCallFine() throws SQLException {
        buildingDao.remove(1);
        verify(mockJdbcTemplate, times(1))
                .update(eq("DELETE FROM building WHERE building_id =?;"),eq(1));
    }

}
