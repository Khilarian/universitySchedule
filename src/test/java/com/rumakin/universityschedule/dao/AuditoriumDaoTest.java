package com.rumakin.universityschedule.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import com.rumakin.universityschedule.model.*;

@DataJpaTest
class AuditoriumDaoTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AuditoriumDao auditoriumDao;

    @Test
    void addShouldExecuteOnceWhenDbCallFine() {
        Building building = new Building("First", "Building");
        Auditorium savedInDb = new Auditorium(15, 35, building);
        Auditorium getFromDb = entityManager.persist(savedInDb);
        assertEquals(savedInDb, getFromDb);
    }

    @Test
    void findByIdhouldExecuteOnceWhenDbCallFineAndRweturnAuditorium() {
        Building building = new Building("First", "Building");
        Auditorium auditorium = new Auditorium(15, 35, building);
        Auditorium savedInDb = entityManager.persist(auditorium);
        Auditorium getFromDb = auditoriumDao.findById(savedInDb.getId()).get();
        assertEquals(savedInDb, getFromDb);
        
    }

    @Test
    void findAllShouldReturnListOfAuditoriumIfAtLeastOneExists() {
        Building building = new Building("First", "Building");
        building = entityManager.persist(building);
        Auditorium auditorium = new Auditorium(15, 35, building);
        Auditorium auditoriumTwo = new Auditorium(16, 30, building);
        Auditorium savedInDb = entityManager.persist(auditorium);
        Auditorium savedInDbTwo = entityManager.persist(auditoriumTwo);
        List<Auditorium> auditoriumsFromDb = (List<Auditorium>) auditoriumDao.findAll();
        List<Auditorium> auditoriumsSaveInDb = Arrays.asList(savedInDb, savedInDbTwo);
        assertEquals(auditoriumsFromDb, auditoriumsSaveInDb);
    }

    @Test
    void delteteShouldExecuteOnceWhenDbCallFine() {
        Building building = new Building("First", "Building");
        building = entityManager.persist(building);
        Auditorium auditorium = new Auditorium(15, 35, building);
        Auditorium auditoriumTwo = new Auditorium(16, 30, building);
        Auditorium savedInDb = entityManager.persist(auditorium);
        Auditorium savedInDbTwo = entityManager.persist(auditoriumTwo);
        entityManager.remove(savedInDb);
        List<Auditorium> auditoriumsFromDb = (List<Auditorium>) auditoriumDao.findAll();
        List<Auditorium> auditoriums = Arrays.asList(savedInDbTwo);
        assertEquals(auditoriumsFromDb, auditoriums);
    }

    @Test
    void updateShouldExecuteOnceWhenDbCallFineAndUodateEntityField() {
        Building building = new Building("First", "Building");
        Auditorium auditorium = new Auditorium(15, 35, building);
        entityManager.persist(auditorium);
        Auditorium getFromDb = auditoriumDao.findById(auditorium.getId()).get();
        int actual = 50;
        getFromDb.setCapacity(actual);
        entityManager.persist(getFromDb);
        int expected = getFromDb.getCapacity();
        assertEquals(expected, actual);
    }

}
