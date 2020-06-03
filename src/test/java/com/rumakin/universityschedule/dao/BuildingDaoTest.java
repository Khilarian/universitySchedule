package com.rumakin.universityschedule.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import com.rumakin.universityschedule.models.*;

@DataJpaTest
class BuildingDaoTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private BuildingDao buildingDao;

    @Test
    public void addShouldExecuteOnceWhenDbCallFine() {
        Building savedInDb = new Building("First", "Building");
        Building getFromDb = entityManager.persist(savedInDb);
        assertEquals(savedInDb, getFromDb);
    }

    @Test
    public void findByIdhouldExecuteOnceWhenDbCallFineAndRweturnAuditorium() {
        Building building = new Building("First", "Building");
        Building savedInDb = entityManager.persist(building);
        Building getFromDb = buildingDao.findById(savedInDb.getId()).get();
        assertEquals(savedInDb, getFromDb);
    }

    @Test
    public void findAllShouldReturnListOfAuditoriumIfAtLeastOneExists() {
        Building building = new Building("First", "Building");
        Building buildingTwo = new Building("First", "Building");
        Building savedInDb = entityManager.persist(building);
        Building savedInDbTwo = entityManager.persist(buildingTwo);
        List<Building> buildingsFromDb = (List<Building>) buildingDao.findAll();
        List<Building> buildingsSaveInDb = Arrays.asList(savedInDb, savedInDbTwo);
        assertEquals(buildingsFromDb, buildingsSaveInDb);
    }

    @Test
    public void delteteShouldExecuteOnceWhenDbCallFine() {
        Building building = new Building("First", "Building");
        Building buildingTwo = new Building("First", "Building");
        Building savedInDb = entityManager.persist(building);
        Building savedInDbTwo = entityManager.persist(buildingTwo);
        entityManager.remove(savedInDb);
        List<Building> buildingsFromDb = (List<Building>) buildingDao.findAll();
        List<Building> buildings = Arrays.asList(savedInDbTwo);
        assertEquals(buildingsFromDb, buildings);
    }

    @Test
    public void updateShouldExecuteOnceWhenDbCallFineAndUodateEntityField() {
        Building building = new Building("First", "Building");
        entityManager.persist(building);
        Building getFromDb = buildingDao.findById(building.getId()).get();
        String actual = "New";
        getFromDb.setAddress(actual);
        entityManager.persist(getFromDb);
        String expected = getFromDb.getAddress();
        assertEquals(expected, actual);
    }

}
