package com.rumakin.universityschedule.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.models.Building;

class BuildingDaoTest {

    @Mock
    private JdbcTemplate mockJdbcTemplate;
    @InjectMocks
    private BuildingDao buildingDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addShouldExecuteOnceWhenDbCallFine() throws SQLException {
        Building building = new Building("Main", "Moscow, Tverskaya, 1");
        Building expected = new Building(1, "Main", "Moscow, Tverskaya, 1");
        Object[] input = { "Main", "Moscow, Tverskaya, 1" };
        when(mockJdbcTemplate.queryForObject(anyString(), any(), eq(Integer.class))).thenReturn(1);
        Building actual = buildingDao.add(building);
        verify(mockJdbcTemplate, times(1)).queryForObject(eq(
                "INSERT INTO building b (b.building_name,b.building_address) VALUES (?,?) RETURNING building_id;"),
                eq(input), eq(Integer.class));
        assertEquals(expected, actual);
    }

    @Test
    void addAllShouldExecuteOnceWhenDbCallFine() throws SQLException {
        Building building = new Building("Main", "Moscow, Tverskaya, 1");
        Building buildingTwo = new Building("Second", "Khimki, Moskovskaya, 23");
        when(mockJdbcTemplate.queryForObject(anyString(), any(), eq(Integer.class))).thenReturn(1, 2);
        Building[] data = { building, buildingTwo };
        List<Building> expected = Arrays.asList(new Building(1, "Main", "Moscow, Tverskaya, 1"),
                new Building(2, "Second", "Khimki, Moskovskaya, 23"));
        List<Building> actual = buildingDao.addAll(Arrays.asList(data));
        assertEquals(expected, actual);
        verify(mockJdbcTemplate, times(1)).queryForObject(eq(
                "INSERT INTO building b (b.building_name,b.building_address) VALUES (?,?) RETURNING building_id;"),
                eq(new Object[] { "Main", "Moscow, Tverskaya, 1" }), eq(Integer.class));
        verify(mockJdbcTemplate, times(1)).queryForObject(eq(
                "INSERT INTO building b (b.building_name,b.building_address) VALUES (?,?) RETURNING building_id;"),
                eq(new Object[] { "Second", "Khimki, Moskovskaya, 23" }), eq(Integer.class));
    }

    @Test
    void findShouldReturnBuildingIfIdExists() throws SQLException {
        Building expected = new Building(1, "Main", "Moskow, Tverskaya, 1");
        when(mockJdbcTemplate.queryForObject(any(String.class), (Object[]) any(Object.class),
                (RowMapper<Building>) any(RowMapper.class))).thenReturn(expected);
        Building actual = buildingDao.find(1);
        assertEquals(expected, actual);
        Object[] input = { 1 };
        verify(mockJdbcTemplate, times(1)).queryForObject(eq("SELECT * FROM building WHERE building_id=?;"),
                eq(input), any(RowMapper.class));
    }

    @Test
    void findAllShouldReturnListOfBuildingsIfAtLeastOneExists() throws SQLException {
        Building building = new Building(1, "Main", "Moskow, Tverskaya, 1");
        Building buildingTwo = new Building(2, "Second", "Khimki, Moskovskaya, 23");
        List<Building> expected = Arrays.asList(building, buildingTwo);
        when(mockJdbcTemplate.query(any(String.class), any(RowMapper.class))).thenReturn(expected);
        List<Building> actual = buildingDao.findAll();
        assertEquals(expected, actual);
        verify(mockJdbcTemplate, times(1)).query(eq("SELECT * FROM building;"),
                any(RowMapper.class));
    }

    @Test
    void removeShouldExecuteOnceWhenDbCallFine() throws SQLException {
        buildingDao.delete(1);
        verify(mockJdbcTemplate, times(1))
                .update(eq("DELETE FROM building WHERE building_id=?;"), eq(1));
    }

}
