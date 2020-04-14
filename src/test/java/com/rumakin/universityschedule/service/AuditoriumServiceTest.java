package com.rumakin.universityschedule.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import com.rumakin.universityschedule.dao.AuditoriumDao;
import com.rumakin.universityschedule.models.*;

class AuditoriumServiceTest {

    @Mock
    private AuditoriumDao mockAuditoriumDao;
    @InjectMocks
    private AuditoriumService auditoriumService = new AuditoriumService(mockAuditoriumDao);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAuditoriumsForGroupOnDateShouldReturnListOfAuditoriumIfAtLeastOneLessonIsPlannedForIputDate() {
        when(mockAuditoriumDao.findAuditoriumOnDate(anyInt(), any()))
                .thenReturn(Arrays.asList(new Auditorium(1, 1, 25, new Building(1, "First", "York"))));
        List<Auditorium> expected = Arrays.asList(new Auditorium(1, 1, 25, new Building(1, "First", "York")));
        Faculty faculty = new Faculty(1,"faculty");
        Group group = new Group(1, "grrroup", faculty);
        LocalDate date = LocalDate.of(2020, 4, 1);
        List<Auditorium> actual = auditoriumService.findAuditoriumsForGroupOnDate(group, date);
        assertEquals(expected, actual);
        verify(mockAuditoriumDao, times(1)).findAuditoriumOnDate(eq(group.getId()), eq(date));
    }

}
