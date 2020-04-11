package com.rumakin.universityschedule.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.*;

import com.rumakin.universityschedule.dao.LessonDao;
import com.rumakin.universityschedule.models.*;
import com.rumakin.universityschedule.models.enums.*;

class LessonServiceTest {

    @Mock
    private LessonDao mockLessonDao;
    @InjectMocks
    private final LessonService lessonService = new LessonService(mockLessonDao);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findExamsForGroupShouldReturnListOfMapsWithSubjectAndDateOfExams() {
        List<Lesson> expected = new ArrayList<>();
        Subject subject = new Subject("history", new Faculty("smth"));
        Auditorium auditorium = new Auditorium(1, 5, 25, new Building(1, "First", "Canter"));
        LessonType type = LessonType.EXAM;
        LocalDate date = LocalDate.of(2020, 6, 1);
        TimeSlot timeSlot = TimeSlot.FIRST;
        Lesson lesson = new Lesson(155, subject, type, auditorium, date, timeSlot);
        expected.add(lesson);
        when(mockLessonDao.findExamsForGroup(anyInt())).thenReturn(expected);
        Group group = new Group(7, "testGroup");
        List<Lesson> actual = lessonService.findExamsForGroup(group);
        assertEquals(expected, actual);
        verify(mockLessonDao, times(1)).findExamsForGroup(eq(7));
    }

}
