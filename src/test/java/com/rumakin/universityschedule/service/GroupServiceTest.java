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
        List<Map<Subject,LocalDate>> expected = new ArrayList<>();
        Map<Subject,LocalDate> exam = new HashMap<>();
        exam.put(new Subject("history", new Faculty("smth")), LocalDate.of(2020, 6, 1));
        expected.add(exam);
        when(mockGroupDao.findExamsForGroup(anyInt())).thenReturn(expected);
        Group group = new Group(7,"testGroup");
        List<Map<Subject,LocalDate>> actual = groupService.findExamsForGroup(group);
        assertEquals(expected, actual);
        verify(mockGroupDao, times(1)).findExamsForGroup(eq(7));
    }

}
