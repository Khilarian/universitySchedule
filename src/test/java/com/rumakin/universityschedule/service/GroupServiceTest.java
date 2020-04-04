package com.rumakin.universityschedule.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.*;

import com.rumakin.universityschedule.dao.GroupDao;
import com.rumakin.universityschedule.models.Auditorium;
import com.rumakin.universityschedule.models.Building;
import com.rumakin.universityschedule.models.Group;

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

}
