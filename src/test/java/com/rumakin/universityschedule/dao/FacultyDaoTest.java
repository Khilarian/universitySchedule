//package com.rumakin.universityschedule.dao;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//import java.sql.SQLException;
//import java.util.*;
//
//import org.junit.jupiter.api.*;
//import org.mockito.*;
//import org.springframework.jdbc.core.*;
//
//import com.rumakin.universityschedule.models.Faculty;
//
//class FacultyDaoTest {
//
//    @Mock
//    private JdbcTemplate mockJdbcTemplate;
//    @InjectMocks
//    private FacultyDao facultyDao;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    void addShouldExecuteOnceWhenDbCallFine() throws SQLException {
//        Faculty faculty = new Faculty("Math");
//        when(mockJdbcTemplate.queryForObject(anyString(), any(), eq(Integer.class))).thenReturn(1);
//        Faculty expected = new Faculty(1, "Math");
//        Faculty actual = facultyDao.add(faculty);
//        assertEquals(expected, actual);
//        verify(mockJdbcTemplate, times(1)).queryForObject(
//                eq("INSERT INTO faculty f (f.faculty_name) VALUES (?) RETURNING faculty_id;"),
//                eq(new Object[] { faculty.getName() }), eq(Integer.class));
//    }
//
//    @Test
//    void addAllShouldExecuteOnceWhenDbCallFine() throws SQLException {
//        Faculty faculty = new Faculty("Math");
//        Faculty facultyTwo = new Faculty("History");
//        Faculty[] data = { faculty, facultyTwo };
//        when(mockJdbcTemplate.queryForObject(anyString(), any(), eq(Integer.class))).thenReturn(1, 2);
//        List<Faculty> expected = Arrays.asList(new Faculty(1, "Math"), new Faculty(2, "History"));
//        List<Faculty> actual = facultyDao.addAll(Arrays.asList(data));
//        assertEquals(expected, actual);
//        verify(mockJdbcTemplate, times(1)).queryForObject(
//                eq("INSERT INTO faculty f (f.faculty_name) VALUES (?) RETURNING faculty_id;"),
//                eq(new Object[] { faculty.getName() }), eq(Integer.class));
//        verify(mockJdbcTemplate, times(1)).queryForObject(
//                eq("INSERT INTO faculty f (f.faculty_name) VALUES (?) RETURNING faculty_id;"),
//                eq(new Object[] { facultyTwo.getName() }), eq(Integer.class));
//    }
//
//    @Test
//    void findByIdShouldReturnFacultyIfIdExists() throws SQLException {
//        Faculty expected = new Faculty("Math");
//        when(mockJdbcTemplate.queryForObject(any(String.class), (Object[]) any(Object.class),
//                (RowMapper<Faculty>) any(RowMapper.class))).thenReturn(expected);
//        Faculty actual = facultyDao.find(1);
//        assertEquals(expected, actual);
//        Object[] input = { 1 };
//        verify(mockJdbcTemplate, times(1)).queryForObject(eq("SELECT * FROM faculty WHERE faculty_id=?;"), eq(input),
//                any(RowMapper.class));
//    }
//
//    @Test
//    void findAllShouldReturnListOfCoursesIfAtLeastOneExists() throws SQLException {
//        Faculty faculty = new Faculty("Math");
//        Faculty facultyTwo = new Faculty("History");
//        List<Faculty> expected = Arrays.asList(faculty, facultyTwo);
//        when(mockJdbcTemplate.query(any(String.class), any(RowMapper.class))).thenReturn(expected);
//        List<Faculty> actual = facultyDao.findAll();
//        assertEquals(expected, actual);
//        verify(mockJdbcTemplate, times(1)).query(eq("SELECT * FROM faculty;"), any(RowMapper.class));
//    }
//
//    @Test
//    void removeShouldExecuteOnceWhenDbCallFine() throws SQLException {
//        facultyDao.delete(1);
//        verify(mockJdbcTemplate, times(1)).update(eq("DELETE FROM faculty WHERE faculty_id=?;"), eq(1));
//    }
//
//}
