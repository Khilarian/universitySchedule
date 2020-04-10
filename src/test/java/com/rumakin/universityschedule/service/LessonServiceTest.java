package com.rumakin.universityschedule.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rumakin.universityschedule.dao.LessonDao;
import com.rumakin.universityschedule.models.Auditorium;
import com.rumakin.universityschedule.models.Building;
import com.rumakin.universityschedule.models.Faculty;
import com.rumakin.universityschedule.models.Group;
import com.rumakin.universityschedule.models.Lesson;
import com.rumakin.universityschedule.models.Subject;
import com.rumakin.universityschedule.models.enums.LessonType;
import com.rumakin.universityschedule.models.enums.TimeSlot;

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
        Auditorium auditorium = new Auditorium(1,5,25,new Building(1,"First","Canter"));
        LessonType type =LessonType.EXAM;
        LocalDate date = LocalDate.of(2020, 6, 1);
        TimeSlot timeSlot = TimeSlot.FIRST;
        Lesson lesson = new Lesson(155,subject,type,auditorium,date,timeSlot);
        expected.add(lesson);
        when(mockLessonDao.findExamsForGroup(anyInt())).thenReturn(expected);
        Group group = new Group(7,"testGroup");
        List<Lesson> actual = lessonService.findExamsForGroup(group);
        assertEquals(expected, actual);
        verify(mockLessonDao, times(1)).findExamsForGroup(eq(7));
    }

}
