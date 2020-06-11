package com.rumakin.universityschedule.service;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rumakin.universityschedule.dao.BuildingDao;
import com.rumakin.universityschedule.exceptions.ResourceNotFoundException;
import com.rumakin.universityschedule.models.*;

@SpringBootTest
class BuildingServiceTest {

    @Autowired
    private BuildingService buildingService;

    @MockBean
    private BuildingDao mockBuildingDao;

    @Test
    public void addShouldExecuteOnceWhenDbCallFine() {
        Building expected =  new Building(1, "First", "York");
        Mockito.when(mockBuildingDao.save(expected)).thenReturn(expected);
        assertEquals(buildingService.add(expected), expected);
    }

    @Test
    public void findByIdShouldExecuteOnceWhenDbCallFineAndReturnAuditorium() {
        Building expected =  new Building(1, "First", "York");
        Mockito.when(mockBuildingDao.findById(1)).thenReturn(Optional.of(expected));
        assertEquals(buildingService.findById(1), expected);
    }
    
    @Test
    public void findByIdShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> buildingService.findById(1));
    }

    @Test
    public void findAllShouldReturnListOfAuditoriumIfAtLeastOneExists() {
        Building building = new Building(10, "First", "Building");
        Building buildingTwo = new Building(11, "Second", "BuildingTwo");
        List<Building> buildings = Arrays.asList(building, buildingTwo);
        Mockito.when(mockBuildingDao.findAll()).thenReturn(buildings);
        assertEquals(buildingService.findAll(), buildings);
    }

    @Test
    public void delteteShouldExecuteOnceWhenDbCallFine() {
        Building building = new Building(10, "First", "Building");
        Mockito.when(mockBuildingDao.findById(1)).thenReturn(Optional.of(building));
        Mockito.when(mockBuildingDao.existsById(building.getId())).thenReturn(false);
        assertFalse(mockBuildingDao.existsById(building.getId()));
    }

    @Test
    public void updateShouldExecuteOnceWhenDbCallFineAndUodateEntityField() {
        Building building = new Building(10, "First", "Building");
        Mockito.when(mockBuildingDao.findById(1)).thenReturn(Optional.of(building));
        building.setAddress("new");
        Mockito.when(mockBuildingDao.save(building)).thenReturn(building);
        assertEquals(buildingService.update(building), building);

    }

}
