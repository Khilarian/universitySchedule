package com.rumakin.universityschedule.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

import java.util.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rumakin.universityschedule.dao.BuildingDao;
import com.rumakin.universityschedule.exception.InvalidEntityException;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.*;

@SpringBootTest
class BuildingServiceTest {

    @Autowired
    private BuildingService buildingService;

    @MockBean
    private BuildingDao mockBuildingDao;

    @Test
    public void addShouldExecuteOnceWhenDbCallFine() {
        Building savedBuilding = new Building(0, "First", "York");
        Building expectedBuilding =  new Building(1, "First", "York");
        Mockito.when(mockBuildingDao.save(savedBuilding)).thenReturn(expectedBuilding);
        assertEquals(buildingService.add(savedBuilding), expectedBuilding);
        Mockito.verify(mockBuildingDao, times(1)).save(savedBuilding);
    }
    
    @Test
    public void addShouldRaiseExceptionIfIdNotEqualZero() {
        Building savedBuilding = new Building(1, "Building", "Address");
        assertThrows(InvalidEntityException.class, () -> buildingService.add(savedBuilding));
    }

    @Test
    public void findByIdShouldExecuteOnceWhenDbCallFineAndReturnBuilding() {
        Building expected =  new Building(1, "First", "York");
        Mockito.when(mockBuildingDao.findById(1)).thenReturn(Optional.of(expected));
        assertEquals(buildingService.findById(1), expected);
        Mockito.verify(mockBuildingDao, times(1)).findById(1);
    }
    
    @Test
    public void findByIdShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> buildingService.findById(1));
        Mockito.verify(mockBuildingDao, times(1)).findById(1);
    }
    
    @Test
    public void findByNameShouldExecuteOnceWhenDbCallFineAndReturnAuditorium() {
        Building expected =  new Building(1, "First", "York");
        Mockito.when(mockBuildingDao.findByName("bb")).thenReturn(Optional.of(expected));
        assertEquals(buildingService.findByName("bb"), expected);
        Mockito.verify(mockBuildingDao, times(1)).findByName("bb");
    }
    
    @Test
    public void findByNameShouldRaiseExceptionIfNameMissed() {
        assertThrows(ResourceNotFoundException.class, () -> buildingService.findByName("bb"));
        Mockito.verify(mockBuildingDao, times(1)).findByName("bb");
    }
    
    @Test
    public void findByAddreddShouldExecuteOnceWhenDbCallFineAndReturnBuilding() {
        Building expected =  new Building(1, "First", "York");
        Mockito.when(mockBuildingDao.findByAddress("bb")).thenReturn(Optional.of(expected));
        assertEquals(buildingService.findByAddress("bb"), expected);
        Mockito.verify(mockBuildingDao, times(1)).findByAddress("bb");
    }
    
    @Test
    public void findByAddressShouldRaiseExceptionIfAddressMissed() {
        assertThrows(ResourceNotFoundException.class, () -> buildingService.findByAddress("bb"));
        Mockito.verify(mockBuildingDao, times(1)).findByAddress("bb");
    }

    @Test
    public void findAllShouldReturnListOfAuditoriumIfAtLeastOneExists() {
        Building building = new Building(10, "First", "Building");
        Building buildingTwo = new Building(11, "Second", "BuildingTwo");
        List<Building> buildings = Arrays.asList(building, buildingTwo);
        Mockito.when(mockBuildingDao.findAll()).thenReturn(buildings);
        assertEquals(buildingService.findAll(), buildings);
        Mockito.verify(mockBuildingDao, times(1)).findAll();
    }

    @Test
    public void deleteShouldExecuteOnceWhenDbCallFine() {
        Building building = new Building(10, "First", "Building");
        buildingService.deleteById(building.getId());
        Mockito.verify(mockBuildingDao, times(1)).deleteById(10);
    }

    @Test
    public void updateShouldExecuteOnceWhenDbCallFineAndUpdateEntityField() {
        Building building = new Building(10, "First", "Building");
        Mockito.when(mockBuildingDao.findById(1)).thenReturn(Optional.of(building));
        building.setAddress("new");
        Mockito.when(mockBuildingDao.save(building)).thenReturn(building);
        assertEquals(buildingService.update(building), building);
        Mockito.verify(mockBuildingDao, times(1)).save(building);
    }
    
    @Test
    public void updateShouldRaiseExceptionIfIdEqualZero() {
        Building updatedBuilding = new Building(0, "Bldng", "Address");
        assertThrows(InvalidEntityException.class, () -> buildingService.update(updatedBuilding));
    }

}
