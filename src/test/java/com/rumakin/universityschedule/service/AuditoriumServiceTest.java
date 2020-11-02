package com.rumakin.universityschedule.service;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.*;

import com.rumakin.universityschedule.dao.AuditoriumDao;
import com.rumakin.universityschedule.exception.*;
import com.rumakin.universityschedule.model.*;

@SpringBootTest
class AuditoriumServiceTest {

    @Autowired
    private AuditoriumService auditoriumService;

    @MockBean
    private AuditoriumDao mockAuditoriumDao;
    
    @MockBean
    private BuildingService mockBuildingService;

    @Test
    public void addShouldExecuteOnceWhenDbCallFine() {
        Building building = new Building(1, "First", "York");
        Auditorium savedAuditorium = new Auditorium(0, 1, 25, building);
        Auditorium expectedAuditorium = new Auditorium(1, 1, 25, building);
        Mockito.when(mockAuditoriumDao.save(savedAuditorium)).thenReturn(expectedAuditorium);
        assertEquals(auditoriumService.add(savedAuditorium), expectedAuditorium);
        Mockito.verify(mockAuditoriumDao, times(1)).save(savedAuditorium);
    }

    @Test
    public void addShouldRaiseExceptionIfIdNotEqualZero() {
        Building building = new Building(1, "First", "York");
        Auditorium savedAuditorium = new Auditorium(1, 1, 25, building);
        assertThrows(InvalidEntityException.class, () -> auditoriumService.add(savedAuditorium));
    }

    @Test
    public void findByIdShouldExecuteOnceWhenDbCallFineAndReturnAuditorium() {
        Auditorium expectedAuditorium = new Auditorium(1, 1, 25, new Building(1, "First", "York"));
        Mockito.when(mockAuditoriumDao.findById(1)).thenReturn(Optional.of(expectedAuditorium));
        assertEquals(auditoriumService.findById(1), expectedAuditorium);
        Mockito.verify(mockAuditoriumDao, times(1)).findById(1);
    }

    @Test
    public void findByIdShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> auditoriumService.findById(1));
        Mockito.verify(mockAuditoriumDao, times(1)).findById(1);
    }
    
    @Test
    public void findByNumberAndBuildingIdShouldExecuteOnceWhenDbCallFineAndReturnAuditorium() {
        Auditorium expectedAuditorium = new Auditorium(1, 1, 25, new Building(1, "First", "York"));
        Mockito.when(mockAuditoriumDao.findByNumberAndBuildingId(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Optional.of(expectedAuditorium));
        assertEquals(auditoriumService.findByNumberAndBuildingId(1,1), expectedAuditorium);
        Mockito.verify(mockAuditoriumDao, times(1)).findByNumberAndBuildingId(1, 1);
    }

    @Test
    public void findByNumberAndBuildingIdShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> auditoriumService.findByNumberAndBuildingId(1, 1));
        Mockito.verify(mockAuditoriumDao, times(1)).findByNumberAndBuildingId(1, 1);
    }

    @Test
    public void findAllShouldReturnListOfAuditoriumIfAtLeastOneExists() {
        Building building = new Building(10, "First", "Building");
        Auditorium auditorium = new Auditorium(15, 35, building);
        Auditorium auditoriumTwo = new Auditorium(16, 30, building);
        List<Auditorium> auditoriums = Arrays.asList(auditorium, auditoriumTwo);
        Mockito.when(mockAuditoriumDao.findAll()).thenReturn(auditoriums);
        assertEquals(auditoriumService.findAll(), auditoriums);
        Mockito.verify(mockAuditoriumDao, times(1)).findAll();
    }

    @Test
    public void deleteShouldExecuteOnceWhenDbCallFine() {
        Building building = new Building(10, "First", "Building");
        Auditorium auditorium = new Auditorium(15, 15, 35, building);
        auditoriumService.deleteById(auditorium.getId());
        Mockito.verify(mockAuditoriumDao, times(1)).deleteById(15);
    }

    @Test
    public void updateShouldExecuteOnceWhenDbCallFineAndUpdateEntityField() {
        Building building = new Building(10, "First", "Building");
        Auditorium updatedAuditorium = new Auditorium(1, 15, 35, building);
        Mockito.when(mockAuditoriumDao.findById(1)).thenReturn(Optional.of(updatedAuditorium));
        updatedAuditorium.setCapacity(20);
        Mockito.when(mockAuditoriumDao.save(updatedAuditorium)).thenReturn(updatedAuditorium);
        assertEquals(auditoriumService.update(updatedAuditorium), updatedAuditorium);
        Mockito.verify(mockAuditoriumDao, times(1)).save(updatedAuditorium);
    }
    
    @Test
    public void updateShouldRaiseExceptionIfIdEqualZero() {
        Building building = new Building(1, "First", "York");
        Auditorium updatedAuditorium = new Auditorium(0, 1, 25, building);
        assertThrows(InvalidEntityException.class, () -> auditoriumService.update(updatedAuditorium));
    }
    
    @Test
    public void getBuildingsShouldReturnAllBuildingsFromDB() {
        Building building = new Building(1, "First", "York");
        Building buildingTwo = new Building(2, "Second", "New");
        List<Building> buildings = Arrays.asList(building,buildingTwo);
        Mockito.when(mockBuildingService.findAll()).thenReturn(buildings);
        assertEquals(auditoriumService.getBuildings(), buildings);
        Mockito.verify(mockBuildingService, times(1)).findAll();
    }

}
