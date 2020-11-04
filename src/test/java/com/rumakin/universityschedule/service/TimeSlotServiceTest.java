package com.rumakin.universityschedule.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.*;

import com.rumakin.universityschedule.dao.*;
import com.rumakin.universityschedule.exception.*;
import com.rumakin.universityschedule.model.*;

@SpringBootTest
class TimeSlotServiceTest {

    @Autowired
    private TimeSlotService timeSlotService;

    @MockBean
    private TimeSlotDao mockTimeSlotDao;

    @Test
    public void addShouldExecuteOnceWhenDbCallFine() {
        TimeSlot savedTimeSlot = new TimeSlot(0, 5, "FIFTH", LocalTime.of(1, 0), LocalTime.of(2, 0));
        TimeSlot expectedTimeSlot = new TimeSlot(1, 5, "FIFTH", LocalTime.of(3, 0), LocalTime.of(4, 0));
        Mockito.when(mockTimeSlotDao.save(savedTimeSlot)).thenReturn(expectedTimeSlot);
        assertEquals(timeSlotService.add(savedTimeSlot), expectedTimeSlot);
        Mockito.verify(mockTimeSlotDao, times(1)).save(savedTimeSlot);
    }

    @Test
    public void addShouldRaiseExceptionIfIdNotEqualZero() {
        TimeSlot savedTimeSlot = new TimeSlot(1, 5, "FIFTH", LocalTime.of(1, 0), LocalTime.of(2, 0));
        assertThrows(InvalidEntityException.class, () -> timeSlotService.add(savedTimeSlot));
    }

    @Test
    public void findByIdShouldExecuteOnceWhenDbCallFineAndReturnTimeSlot() {
        TimeSlot expectedTimeSlot = new TimeSlot(1, 5, "FIFTH", LocalTime.of(1, 0), LocalTime.of(2, 0));
        Mockito.when(mockTimeSlotDao.findById(1)).thenReturn(Optional.of(expectedTimeSlot));
        assertEquals(timeSlotService.findById(1), expectedTimeSlot);
        Mockito.verify(mockTimeSlotDao, times(1)).findById(1);
    }

    @Test
    public void findByIdShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> timeSlotService.findById(1));
        Mockito.verify(mockTimeSlotDao, times(1)).findById(1);
    }

    @Test
    public void findByNumberShouldExecuteOnceWhenDbCallFineAndReturnTimeSlot() {
        TimeSlot expected = new TimeSlot(1, 6, "SIXTH", LocalTime.of(1, 0), LocalTime.of(2, 0));
        Mockito.when(mockTimeSlotDao.findByNumber(Mockito.anyInt())).thenReturn(Optional.of(expected));
        assertEquals(timeSlotService.findByNumber(6), expected);
        Mockito.verify(mockTimeSlotDao, times(1)).findByNumber(6);
    }

    @Test
    public void findByNumberShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> timeSlotService.findByNumber(5));
        Mockito.verify(mockTimeSlotDao, times(1)).findByNumber(5);
    }

    @Test
    public void findAllShouldReturnListOfAuditoriumIfAtLeastOneExists() {
        TimeSlot timeSlot = new TimeSlot(1, 5, "FIFTH", LocalTime.of(1, 0), LocalTime.of(2, 0));
        TimeSlot timeSlotTwo = new TimeSlot(2, 6, "SIXTH", LocalTime.of(3, 0), LocalTime.of(4, 0));
        List<TimeSlot> timeSlots = Arrays.asList(timeSlot, timeSlotTwo);
        Mockito.when(mockTimeSlotDao.findAll()).thenReturn(timeSlots);
        assertEquals(timeSlotService.findAll(), timeSlots);
        Mockito.verify(mockTimeSlotDao, times(1)).findAll();
    }

    @Test
    public void deleteShouldExecuteOnceWhenDbCallFine() {
        TimeSlot timeSlot = new TimeSlot(1, 5, "FIFTH", LocalTime.of(1, 0), LocalTime.of(2, 0));
        timeSlotService.deleteById(timeSlot.getId());
        Mockito.verify(mockTimeSlotDao, times(1)).deleteById(1);
    }

    @Test
    public void updateShouldExecuteOnceWhenDbCallFineAndUpdateEntityField() {
        TimeSlot updatedTimeSlot = new TimeSlot(1, 5, "FIFTH", LocalTime.of(1, 0), LocalTime.of(2, 0));
        Mockito.when(mockTimeSlotDao.findById(1)).thenReturn(Optional.of(updatedTimeSlot));
        Mockito.when(mockTimeSlotDao.save(updatedTimeSlot)).thenReturn(updatedTimeSlot);
        assertEquals(timeSlotService.update(updatedTimeSlot), updatedTimeSlot);
        Mockito.verify(mockTimeSlotDao, times(1)).save(updatedTimeSlot);
    }

    @Test
    public void updateShouldRaiseExceptionIfIdEqualZero() {
        TimeSlot updatedTimeSlot = new TimeSlot(0, 5, "FIFTH", LocalTime.of(1, 0), LocalTime.of(2, 0));
        assertThrows(InvalidEntityException.class, () -> timeSlotService.update(updatedTimeSlot));
    }

}
