package com.rumakin.universityschedule.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rumakin.universityschedule.dao.AuditoriumDao;
import com.rumakin.universityschedule.models.Auditorium;
import com.rumakin.universityschedule.models.Building;
import com.rumakin.universityschedule.models.Group;

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
        Group group = new Group(1, "grrroup");
        LocalDate date = LocalDate.of(2020, 4, 1);
        List<Auditorium> actual = auditoriumService.findAuditoriumsForGroupOnDate(group, date);
        assertEquals(expected, actual);
        verify(mockAuditoriumDao, times(1)).findAuditoriumOnDate(eq(group.getId()), eq(date));
    }

}
