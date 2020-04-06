package com.rumakin.universityschedule.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.rumakin.universityschedule.models.*;
import com.rumakin.universityschedule.models.enums.LessonType;
import com.rumakin.universityschedule.models.enums.TimeSlot;

class GroupDaoTest {

    @Mock
    private JdbcTemplate mockJdbcTemplate;
    @Mock
    private AuditoriumDao mockAuditoriumDao;

    @Mock
    private LessonDao mockLessonDao;
    @Mock
    private SqlRowSet mockSqlRowSet;
    @InjectMocks
    private GroupDao groupDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAuditoriumOnDateShouldExecuteOnceIfDbCallFine() {
        when(mockJdbcTemplate.queryForList(anyString(), any(), eq(Integer.class))).thenReturn(Arrays.asList(1, 2));
        when(mockAuditoriumDao.find(anyInt())).thenReturn(new Auditorium(1, 1, 15, new Building(1, "First", "Moscow")),
                new Auditorium(2, 2, 20, new Building(1, "First", "Moscow")));
        List<Auditorium> expected = Arrays.asList(new Auditorium(1, 1, 15, new Building(1, "First", "Moscow")),
                new Auditorium(2, 2, 20, new Building(1, "First", "Moscow")));
        Group group = new Group(1, "Best Group");
        LocalDate date = LocalDate.of(2015, 3, 2);
        List<Auditorium> actual = groupDao.findAuditoriumOnDate(group.getId(), date);
        assertEquals(expected, actual);
        verify(mockJdbcTemplate, times(1)).queryForList(
                eq("SELECT l.auditorium_id FROM group g INNER JOIN lesson_group lg ON g.group_id=lg.group_id "
                        + "INNER JOIN lesson l ON lg.lesson_id=l.lesson_id WHERE g.group_id=? AND l.date=?;"),
                eq(new Object[] { 1, java.sql.Date.valueOf(date) }), eq(Integer.class));
        verify(mockAuditoriumDao, times(1)).find(1);
        verify(mockAuditoriumDao, times(1)).find(2);
    }

    @Test
    void findExamsForGroupShouldExecuteOnceIfDbCallFineAndReturnListOfExamsIfAtLeastOneExists() {
        List<Lesson> expected = new ArrayList<>();
        Subject subject = new Subject("history", new Faculty("smth"));
        Auditorium auditorium = new Auditorium(1,5,25,new Building(1,"First","Canter"));
        LessonType type =LessonType.EXAM;
        LocalDate date = LocalDate.of(2020, 6, 1);
        TimeSlot timeSlot = TimeSlot.FIRST;
        Lesson lesson = new Lesson(155,subject,type,auditorium,date,timeSlot);
        expected.add(lesson);
        when(mockJdbcTemplate.queryForList(anyString(), any(), eq(Integer.class))).thenReturn(Arrays.asList(155));
        when(mockLessonDao.find(anyInt())).thenReturn(lesson);
        List<Lesson> actual = groupDao.findExamsForGroup(5);
        assertEquals(expected, actual);
        verify(mockLessonDao,times(1)).find(155);
    }

    @Test
    void addShouldExecuteOnceWhenDbCallFine() throws SQLException {
        Group group = new Group("First Group");
        when(mockJdbcTemplate.queryForObject(anyString(), any(), eq(Integer.class))).thenReturn(1);
        Group expected = new Group(1, "First Group");
        Group actual = groupDao.add(group);
        assertEquals(expected, actual);
        verify(mockJdbcTemplate, times(1)).queryForObject(
                eq("INSERT INTO group g (g.group_name) VALUES (?) RETURNING group_id;"),
                eq(new Object[] { "First Group" }), eq(Integer.class));
    }

    @Test
    void addAllShouldExecuteOnceWhenDbCallFine() throws SQLException {
        Group group = new Group("First Group");
        Group groupTwo = new Group("Second Group");
        Group[] data = { group, groupTwo };
        when(mockJdbcTemplate.queryForObject(anyString(), any(), eq(Integer.class))).thenReturn(1, 2);
        List<Group> expected = Arrays.asList(new Group(1, "First Group"), new Group(2, "Second Group"));
        List<Group> actual = groupDao.addAll(Arrays.asList(data));
        assertEquals(expected, actual);
        verify(mockJdbcTemplate, times(1)).queryForObject(
                eq("INSERT INTO group g (g.group_name) VALUES (?) RETURNING group_id;"),
                eq(new Object[] { group.getName() }), eq(Integer.class));
        verify(mockJdbcTemplate, times(1)).queryForObject(
                eq("INSERT INTO group g (g.group_name) VALUES (?) RETURNING group_id;"),
                eq(new Object[] { groupTwo.getName() }), eq(Integer.class));
    }

    @Test
    void findByIdShouldReturnGroupIfIdExists() throws SQLException {
        Group expected = new Group("First Group");
        when(mockJdbcTemplate.queryForObject(any(String.class), (Object[]) any(Object.class),
                (RowMapper<Group>) any(RowMapper.class))).thenReturn(expected);
        Group actual = groupDao.find(1);
        assertEquals(expected, actual);
        Object[] input = { 1 };
        verify(mockJdbcTemplate, times(1)).queryForObject(eq("SELECT * FROM group WHERE group_id=?;"), eq(input),
                any(RowMapper.class));
    }

    @Test
    void findAllShouldReturnListOfCoursesIfAtLeastOneExists() throws SQLException {
        Group group = new Group("First Group");
        Group groupTwo = new Group("Second Group");
        List<Group> expected = Arrays.asList(group, groupTwo);
        when(mockJdbcTemplate.query(any(String.class), any(RowMapper.class))).thenReturn(expected);
        List<Group> actual = groupDao.findAll();
        assertEquals(expected, actual);
        verify(mockJdbcTemplate, times(1)).query(eq("SELECT * FROM group;"), any(RowMapper.class));
    }

    @Test
    void removeShouldExecuteOnceWhenDbCallFine() throws SQLException {
        groupDao.remove(1);
        verify(mockJdbcTemplate, times(1)).update(eq("DELETE FROM group WHERE group_id=?;"), eq(1));
    }
}
