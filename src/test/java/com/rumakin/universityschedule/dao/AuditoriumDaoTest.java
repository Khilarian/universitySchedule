//package com.rumakin.universityschedule.dao;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import org.springframework.jdbc.core.*;
//
//import com.rumakin.universityschedule.models.*;
//
//class AuditoriumDaoTest {
//
//    @Mock
//    private JdbcTemplate mockJdbcTemplate;
//    @Mock
//    private BuildingDao mockBuildingDao;
//    @Mock
//    private Building mockBuilding;
//    @Mock
//    private Building mockBuildingTwo;
//    @InjectMocks
//    private AuditoriumDao auditoriumDao = new AuditoriumDao(mockJdbcTemplate, mockBuildingDao);
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    void findAuditoriumOnDateShouldExecuteOnceIfDbCallFine() {
//        when(mockJdbcTemplate.queryForList(anyString(), any(), eq(Integer.class))).thenReturn(Arrays.asList(1, 2));
//        Auditorium one = new Auditorium(1, 1, 15, new Building(1, "First", "Moscow"));
//        Auditorium two = new Auditorium(2, 2, 20, new Building(1, "First", "Moscow"));
//        when(mockJdbcTemplate.queryForObject(any(String.class), (Object[]) any(Object.class),
//                (RowMapper<Auditorium>) any(RowMapper.class))).thenReturn(one, two);
//        List<Auditorium> expected = Arrays.asList(new Auditorium(1, 1, 15, new Building(1, "First", "Moscow")),
//                new Auditorium(2, 2, 20, new Building(1, "First", "Moscow")));
//        Group group = new Group(1, "Best Group", new Faculty("Faculty"));
//        LocalDate date = LocalDate.of(2015, 3, 2);
//        List<Auditorium> actual = auditoriumDao.findAuditoriumOnDate(group.getId(), date);
//        assertEquals(expected, actual);
//        verify(mockJdbcTemplate, times(1)).queryForList(eq(
//                "SELECT a.auditorium_id FROM auditorium a INNER JOIN lesson l ON a.auditorium_id=l.auditorium_id "
//                + "INNER JOIN lesson_group lg ON l.lesson_id=lg.lesson_id WHERE lg.group_id=? AND l.date=?;"),
//                eq(new Object[] { 1, java.sql.Date.valueOf(date) }), eq(Integer.class));
//    }
//
//    @Test
//    void addShouldExecuteOnceWhenDbCallFine() throws SQLException {
//        Auditorium auditorium = new Auditorium(1, 35, mockBuilding);
//        when(mockBuilding.getId()).thenReturn(1);
//        when(mockJdbcTemplate.queryForObject(anyString(), any(), eq(Integer.class))).thenReturn(1);
//        auditoriumDao.add(auditorium);
//        verify(mockBuilding, times(1)).getId();
//        verify(mockJdbcTemplate, times(1)).queryForObject(eq(
//                "INSERT INTO auditorium a (a.auditorium_number,a.capacity,a.building_id) VALUES (?,?,?) RETURNING auditorium_id;"),
//                eq(new Object[] { 1, 35, 1 }), eq(Integer.class));
//    }
//
//    @Test
//    void addAllShouldExecuteOnceWhenDbCallFine() throws SQLException {
//        Auditorium auditorium = new Auditorium(1, 35, mockBuilding);
//        Auditorium auditoriumTwo = new Auditorium(2, 15, mockBuildingTwo);
//        when(mockBuilding.getId()).thenReturn(1);
//        when(mockBuildingTwo.getId()).thenReturn(2);
//        when(mockJdbcTemplate.queryForObject(anyString(), any(), eq(Integer.class))).thenReturn(1, 2);
//        Auditorium[] data = { auditorium, auditoriumTwo };
//        List<Auditorium> expected = Arrays.asList(new Auditorium(1, 1, 35, mockBuilding),
//                new Auditorium(2, 2, 15, mockBuildingTwo));
//        List<Auditorium> actual = auditoriumDao.addAll(Arrays.asList(data));
//        assertEquals(expected, actual);
//        verify(mockJdbcTemplate, times(1)).queryForObject(eq(
//                "INSERT INTO auditorium a (a.auditorium_number,a.capacity,a.building_id) VALUES (?,?,?) RETURNING auditorium_id;"),
//                eq(new Object[] { 1, 35, 1 }), eq(Integer.class));
//        verify(mockJdbcTemplate, times(1)).queryForObject(eq(
//                "INSERT INTO auditorium a (a.auditorium_number,a.capacity,a.building_id) VALUES (?,?,?) RETURNING auditorium_id;"),
//                eq(new Object[] { 2, 15, 2 }), eq(Integer.class));
//    }
//
//    @Test
//    void findShouldReturnPersonIfIdExists() throws SQLException {
//        Auditorium expected = new Auditorium(1, 1, 35, mockBuilding);
//        when(mockJdbcTemplate.queryForObject(any(String.class), (Object[]) any(Object.class),
//                (RowMapper<Auditorium>) any(RowMapper.class))).thenReturn(expected);
//        Auditorium actual = auditoriumDao.find(1);
//        assertEquals(expected, actual);
//        Object[] input = { 1 };
//        verify(mockJdbcTemplate, times(1)).queryForObject(eq("SELECT * FROM auditorium WHERE auditorium_id=?;"),
//                eq(input), any(RowMapper.class));
//    }
//
//    @Test
//    void findAllShouldReturnListOfCoursesIfAtLeastOneExists() throws SQLException {
//        Auditorium auditorium = new Auditorium(1, 1, 35, mockBuilding);
//        Auditorium auditoriumTwo = new Auditorium(2, 2, 15, mockBuildingTwo);
//        List<Auditorium> expected = Arrays.asList(auditorium, auditoriumTwo);
//        when(mockJdbcTemplate.query(any(String.class), any(RowMapper.class))).thenReturn(expected);
//        List<Auditorium> actual = auditoriumDao.findAll();
//        assertEquals(expected, actual);
//        verify(mockJdbcTemplate, times(1)).query(eq("SELECT * FROM auditorium;"), any(RowMapper.class));
//    }
//
//    @Test
//    void removeShouldExecuteOnceWhenDbCallFine() throws SQLException {
//        auditoriumDao.delete(1);
//        verify(mockJdbcTemplate, times(1)).update(eq("DELETE FROM auditorium WHERE auditorium_id=?;"), eq(1));
//    }
//
//}
