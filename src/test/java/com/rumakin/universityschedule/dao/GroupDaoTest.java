package com.rumakin.universityschedule.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.*;
import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.rumakin.universityschedule.models.*;

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
    void addShouldExecuteOnceWhenDbCallFine() throws SQLException {
        Faculty faculty = new Faculty(7, "faculty");
        Group group = new Group("First Group", faculty);
        when(mockJdbcTemplate.queryForObject(anyString(), any(), eq(Integer.class))).thenReturn(1);
        Group expected = new Group(1, "First Group", faculty);
        Group actual = groupDao.add(group);
        assertEquals(expected, actual);
        verify(mockJdbcTemplate, times(1)).queryForObject(
                eq("INSERT INTO groups g (g.group_name,g.faculty_id) VALUES (?,?) RETURNING group_id;"),
                eq(new Object[] { "First Group", 7 }), eq(Integer.class));
    }

    @Test
    void addAllShouldExecuteOnceWhenDbCallFine() throws SQLException {
        Faculty faculty = new Faculty(5, "faculty");
        Group group = new Group("First Group", faculty);
        Group groupTwo = new Group("Second Group", faculty);
        Group[] data = { group, groupTwo };
        when(mockJdbcTemplate.queryForObject(anyString(), any(), eq(Integer.class))).thenReturn(1, 2);
        List<Group> expected = Arrays.asList(new Group(1, "First Group", faculty),
                new Group(2, "Second Group", faculty));
        List<Group> actual = groupDao.addAll(Arrays.asList(data));
        assertEquals(expected, actual);
        verify(mockJdbcTemplate, times(1)).queryForObject(
                eq("INSERT INTO groups g (g.group_name,g.faculty_id) VALUES (?,?) RETURNING group_id;"),
                eq(new Object[] { group.getName(), 5 }), eq(Integer.class));
        verify(mockJdbcTemplate, times(1)).queryForObject(
                eq("INSERT INTO groups g (g.group_name,g.faculty_id) VALUES (?,?) RETURNING group_id;"),
                eq(new Object[] { groupTwo.getName(), 5 }), eq(Integer.class));
    }

    @Test
    void findByIdShouldReturnGroupIfIdExists() throws SQLException {
        Faculty faculty = new Faculty("faculty");
        Group expected = new Group("First Group", faculty);
        when(mockJdbcTemplate.queryForObject(any(String.class), (Object[]) any(Object.class),
                (RowMapper<Group>) any(RowMapper.class))).thenReturn(expected);
        Group actual = groupDao.find(1);
        assertEquals(expected, actual);
        Object[] input = { 1 };
        verify(mockJdbcTemplate, times(1)).queryForObject(eq("SELECT * FROM groups WHERE group_id=?;"), eq(input),
                any(RowMapper.class));
    }

    @Test
    void findAllShouldReturnListOfCoursesIfAtLeastOneExists() throws SQLException {
        Faculty faculty = new Faculty("faculty");
        Group group = new Group("First Group", faculty);
        Group groupTwo = new Group("Second Group", faculty);
        List<Group> expected = Arrays.asList(group, groupTwo);
        when(mockJdbcTemplate.query(any(String.class), any(RowMapper.class))).thenReturn(expected);
        List<Group> actual = groupDao.findAll();
        assertEquals(expected, actual);
        verify(mockJdbcTemplate, times(1)).query(eq("SELECT * FROM groups;"), any(RowMapper.class));
    }

    @Test
    void removeShouldExecuteOnceWhenDbCallFine() throws SQLException {
        groupDao.remove(1);
        verify(mockJdbcTemplate, times(1)).update(eq("DELETE FROM groups WHERE group_id=?;"), eq(1));
    }
}
