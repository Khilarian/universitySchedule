package com.rumakin.universityschedule.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.models.*;

class AuditoriumDaoTest {

    @Mock
    private JdbcTemplate mockJdbcTemplate;
    @Mock
    private BuildingDao mockBuildingDao;
    @Mock
    private Building mockBuilding;
    @Mock
    private Building mockBuildingTwo;
    @InjectMocks
    private AuditoriumDao auditoriumDao = new AuditoriumDao(mockJdbcTemplate, mockBuildingDao);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addShouldExecuteOnceWhenDbCallFine() throws SQLException {
        Auditorium auditorium = new Auditorium(1, 35, mockBuilding);
        when(mockBuilding.getId()).thenReturn(1);
        when(mockJdbcTemplate.queryForObject(anyString(), any(), eq(Integer.class))).thenReturn(1);
        auditoriumDao.add(auditorium);
        verify(mockBuilding, times(1)).getId();
        verify(mockJdbcTemplate, times(1)).queryForObject(eq(
                "INSERT INTO auditorium a (a.number_id,a.capacity,a.building_id) VALUES (?,?,?) RETURNING auditorium_id;"),
                eq(new Object[] { 1, 35, 1 }), eq(Integer.class));
    }

    @Test
    void addAllShouldExecuteOnceWhenDbCallFine() throws SQLException {
        Auditorium auditorium = new Auditorium(1, 35, mockBuilding);
        Auditorium auditoriumTwo = new Auditorium(2, 15, mockBuildingTwo);
        when(mockBuilding.getId()).thenReturn(1);
        when(mockBuildingTwo.getId()).thenReturn(2);
        when(mockJdbcTemplate.queryForObject(anyString(), any(), eq(Integer.class))).thenReturn(1, 2);
        Auditorium[] data = { auditorium, auditoriumTwo };
        List<Auditorium> expected = Arrays.asList(new Auditorium(1, 1, 35, mockBuilding),
                new Auditorium(2, 2, 15, mockBuildingTwo));
        List<Auditorium> actual = auditoriumDao.addAll(Arrays.asList(data));
        assertEquals(expected, actual);
        verify(mockJdbcTemplate, times(1)).queryForObject(eq(
                "INSERT INTO auditorium a (a.number_id,a.capacity,a.building_id) VALUES (?,?,?) RETURNING auditorium_id;"),
                eq(new Object[] { 1, 35, 1 }), eq(Integer.class));
        verify(mockJdbcTemplate, times(1)).queryForObject(eq(
                "INSERT INTO auditorium a (a.number_id,a.capacity,a.building_id) VALUES (?,?,?) RETURNING auditorium_id;"),
                eq(new Object[] { 2, 15, 2 }), eq(Integer.class));
    }

    @Test
    void findShouldReturnPersonIfIdExists() throws SQLException {
        Auditorium expected = new Auditorium(1, 1, 35, mockBuilding);
        when(mockJdbcTemplate.queryForObject(any(String.class), (Object[]) any(Object.class),
                (RowMapper<Auditorium>) any(RowMapper.class))).thenReturn(expected);
        Auditorium actual = auditoriumDao.find(1);
        assertEquals(expected, actual);
        Object[] input = { 1 };
        verify(mockJdbcTemplate, times(1)).queryForObject(eq("SELECT * FROM auditorium WHERE auditorium_id=?;"),
                eq(input), any(RowMapper.class));
    }

    @Test
    void findAllShouldReturnListOfCoursesIfAtLeastOneExists() throws SQLException {
        Auditorium auditorium = new Auditorium(1, 1, 35, mockBuilding);
        Auditorium auditoriumTwo = new Auditorium(2, 2, 15, mockBuildingTwo);
        List<Auditorium> expected = Arrays.asList(auditorium, auditoriumTwo);
        when(mockJdbcTemplate.query(any(String.class), any(RowMapper.class))).thenReturn(expected);
        List<Auditorium> actual = auditoriumDao.findAll();
        assertEquals(expected, actual);
        verify(mockJdbcTemplate, times(1)).query(eq("SELECT * FROM auditorium;"), any(RowMapper.class));
    }

    @Test
    void removeShouldExecuteOnceWhenDbCallFine() throws SQLException {
        auditoriumDao.remove(1);
        verify(mockJdbcTemplate, times(1)).update(eq("DELETE FROM auditorium WHERE auditorium_id=?;"), eq(1));
    }

}
