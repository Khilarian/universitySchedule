package com.rumakin.universityschedule.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.rumakin.universityschedule.models.*;
import com.rumakin.universityschedule.models.enums.*;

class LessonDaoTest {

    @Mock
    private JdbcTemplate mockJdbcTemplate;
    @Mock
    private AuditoriumDao mockAuditoriumDao;
    @Mock
    private SubjectDao mockSubjectDao;
    @Mock
    private TeacherDao mockTeacherDao;
    @Mock
    private GroupDao mockGroupDao;
    @InjectMocks
    private LessonDao lessonDao = new LessonDao(mockJdbcTemplate, mockAuditoriumDao, mockSubjectDao, mockTeacherDao,
            mockGroupDao);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findExamsForGroupShouldExecuteOnceIfDbCallFineAndReturnListOfExamsIfAtLeastOneExists() {
        List<Lesson> expected = new ArrayList<>();
        Subject subject = new Subject("history", new Faculty("smth"));
        Auditorium auditorium = new Auditorium(1, 5, 25, new Building(1, "First", "Canter"));
        LessonType type = LessonType.EXAM;
        LocalDate date = LocalDate.of(2020, 6, 1);
        TimeSlot timeSlot = TimeSlot.FIRST;
        Lesson lesson = new Lesson(155, subject, type, auditorium, date, timeSlot);
        expected.add(lesson);
        when(mockJdbcTemplate.queryForList(anyString(), any(), eq(Integer.class))).thenReturn(Arrays.asList(155));
        when(mockJdbcTemplate.queryForObject(any(String.class), (Object[]) any(Object.class),
                (RowMapper<Lesson>) any(RowMapper.class))).thenReturn(lesson);
        List<Lesson> actual = lessonDao.findExamsForGroup(5);
        assertEquals(expected, actual);
        verify(mockJdbcTemplate, times(1)).queryForList(
                eq("SELECT l.lesson_id FROM lesson l INNER JOIN lesson_group lg ON l.lesson_id=lg.lesson_id "
                        + "INNER JOIN lessonType lt ON l.lesson_type_name=lt.lesson_type_name WHERE lg.group_id=? AND lt.lesson_type_name=EXAM;"),
                eq(new Object[] { 5 }), eq(Integer.class));
    }

}
