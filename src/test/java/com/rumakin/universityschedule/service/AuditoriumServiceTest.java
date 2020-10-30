package com.rumakin.universityschedule.service;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rumakin.universityschedule.dao.AuditoriumDao;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.*;

@SpringBootTest
class AuditoriumServiceTest {

    @Autowired
    private AuditoriumService auditoriumService;

    @MockBean
    private AuditoriumDao mockAuditoriumDao;

    @Test
    public void addShouldExecuteOnceWhenDbCallFine() {
        Building building = new Building(1, "First", "York");
        Auditorium saved = new Auditorium(0, 1, 25, building);
        Auditorium expected = new Auditorium(1, 1, 25, building);
        Mockito.when(mockAuditoriumDao.save(saved)).thenReturn(expected);
        assertEquals(auditoriumService.add(saved), expected);
    }

    @Test
    public void findByIdShouldExecuteOnceWhenDbCallFineAndReturnAuditorium() {
        Auditorium expected = new Auditorium(1, 1, 25, new Building(1, "First", "York"));
        Mockito.when(mockAuditoriumDao.findById(1)).thenReturn(Optional.of(expected));
        assertEquals(auditoriumService.findById(1), expected);
    }

    @Test
    public void findByIdShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> auditoriumService.findById(1));
    }

    @Test
    public void findAllShouldReturnListOfAuditoriumIfAtLeastOneExists() {
        Building building = new Building(10, "First", "Building");
        Auditorium auditorium = new Auditorium(15, 35, building);
        Auditorium auditoriumTwo = new Auditorium(16, 30, building);
        List<Auditorium> auditoriums = Arrays.asList(auditorium, auditoriumTwo);
        Mockito.when(mockAuditoriumDao.findAll()).thenReturn(auditoriums);
        assertEquals(auditoriumService.findAll(), auditoriums);
    }

    @Test
    public void delteteShouldExecuteOnceWhenDbCallFine() {
        Building building = new Building(10, "First", "Building");
        Auditorium auditorium = new Auditorium(15, 35, building);
        Mockito.when(mockAuditoriumDao.findById(1)).thenReturn(Optional.of(auditorium));
        Mockito.when(mockAuditoriumDao.existsById(auditorium.getId())).thenReturn(false);
        assertFalse(mockAuditoriumDao.existsById(auditorium.getId()));
    }

    @Test
    public void updateShouldExecuteOnceWhenDbCallFineAndUodateEntityField() {
        Building building = new Building(10, "First", "Building");
        Auditorium auditorium = new Auditorium(1, 15, 35, building);
        Mockito.when(mockAuditoriumDao.findById(1)).thenReturn(Optional.of(auditorium));
        auditorium.setCapacity(20);
        Mockito.when(mockAuditoriumDao.save(auditorium)).thenReturn(auditorium);
        assertEquals(auditoriumService.update(auditorium), auditorium);

    }

}
