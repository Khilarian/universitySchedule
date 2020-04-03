package com.rumakin.universityschedule.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.rumakin.universityschedule.dao.AuditoriumDao;
import com.rumakin.universityschedule.exceptions.DaoException;
import com.rumakin.universityschedule.models.Auditorium;
import com.rumakin.universityschedule.models.Building;

class AuditoriumServiceTest {
    
    @Mock
    AuditoriumDao mockAuditoriumDao;
    AuditoriumService auditoriumService = new AuditoriumService(mockAuditoriumDao);

    @Test
    void addShouldExecuteOnceWhenDbCallFine() throws SQLException {
        doNothing().when(mockAuditoriumDao).add(any());
        Auditorium auditorium = new Auditorium(5,25,new Building("Tver","Krasnoselskaya 1"));
        auditoriumService.add(auditorium);
        verify(mockAuditoriumDao, times(1)).add(auditorium);
    }
    
    @Test
    void addAllShouldExecuteOnceWhenDbCallFine() throws SQLException {
        doNothing().when(mockAuditoriumDao).addAll(any());
        Auditorium auditorium = new Auditorium(5,25,new Building("Tver","Krasnoselskaya 1"));
        Auditorium auditoriumTwo = new Auditorium(2,30,new Building("Tver","Krasnoselskaya 1"));
        List<Auditorium> auditoriums = Arrays.asList(auditorium,auditoriumTwo);
        auditoriumService.addAll(auditoriums);
        verify(mockAuditoriumDao, times(1)).addAll(auditoriums);
    }

    @Test
    void findShouldReturnAuditoriumIfIdExists() throws SQLException {
        Auditorium expected = new Auditorium(1,5,25,new Building("Tver","Krasnoselskaya 1"));
        when(auditoriumService.find(anyInt())).thenReturn(expected);
        Auditorium actual = auditoriumService.find(20);
        assertEquals(expected, actual);
        verify(mockAuditoriumDao, times(1)).find(20);
    }
    
    @Test
    void findShouldRaiseExceptionIfIdMissedInDb() throws SQLException {
        when(auditoriumService.find(anyInt())).thenThrow(DaoException.class);
        assertThrows(DaoException.class, ()-> auditoriumService.find(10));
        verify(mockAuditoriumDao, times(1)).find(10);
    }
    
    @Test
    void findAllShouldReturnAuditoriumsIfAtLeastOneExists() throws SQLException {
        Auditorium auditorium = new Auditorium(1,5,25,new Building("Tver","Krasnoselskaya 1"));
        Auditorium auditoriumTwo = new Auditorium(2,2,30,new Building("Tver","Krasnoselskaya 1"));
        List<Auditorium> expected = Arrays.asList(auditorium,auditoriumTwo);
        when(auditoriumService.findAll()).thenReturn(expected);
        List<Auditorium> actual = auditoriumService.findAll();
        assertEquals(expected, actual);
        verify(mockAuditoriumDao, times(1)).findAll();
    }
    
    @Test
    void findAllShouldRaiseExceptionIfIdMissedInDb() throws SQLException {
        when(auditoriumService.findAll()).thenThrow(DaoException.class);
        assertThrows(DaoException.class, ()-> auditoriumService.findAll());
        verify(mockAuditoriumDao, times(1)).findAll();
    }
    
    @Test
    void removeShouldExecuteOnceWhenDbCallFine() throws SQLException {
        doNothing().when(mockAuditoriumDao).remove(anyInt());
        auditoriumService.remove(6);
        verify(mockAuditoriumDao, times(1)).remove(6);
    }
}
