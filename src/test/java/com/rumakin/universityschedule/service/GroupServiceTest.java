package com.rumakin.universityschedule.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.*;

import com.rumakin.universityschedule.dao.*;
import com.rumakin.universityschedule.models.*;
import com.rumakin.universityschedule.models.enums.LessonType;
import com.rumakin.universityschedule.models.enums.TimeSlot;

class GroupServiceTest {
    
    @Mock
    private GroupDao mockGroupDao;
    @InjectMocks
    private GroupService groupService;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAuditoriumsForGroupOnDateShouldReturnListOfAuditoriumIfAtLeastOneLessonIsPlannedForIputDate() {
        when(mockGroupDao.findAuditoriumOnDate(anyInt(), any())).thenReturn(Arrays.asList(new Auditorium(1,1,25,new Building(1,"First","York"))));
        List<Auditorium> expected = Arrays.asList(new Auditorium(1,1,25,new Building(1,"First","York")));
        Group group = new Group(1,"grrroup");
        LocalDate date = LocalDate.of(2020, 4, 1);
        List<Auditorium> actual = groupService.findAuditoriumsForGroupOnDate(group, date);
        assertEquals(expected, actual);
        verify(mockGroupDao, times(1)).findAuditoriumOnDate(eq(1), eq(date));
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
        when(mockGroupDao.findExamsForGroup(anyInt())).thenReturn(expected);
        Group group = new Group(7,"testGroup");
        List<Lesson> actual = groupService.findExamsForGroup(group);
        assertEquals(expected, actual);
        verify(mockGroupDao, times(1)).findExamsForGroup(eq(7));
    }

}
