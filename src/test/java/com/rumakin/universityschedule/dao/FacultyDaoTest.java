package com.rumakin.universityschedule.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import com.rumakin.universityschedule.model.*;

@DataJpaTest
class FacultyDaoTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private FacultyDao facultyDao;

    @Test
    void addShouldExecuteOnceWhenDbCallFine() {
        Faculty savedInDb = new Faculty("Faculty");
        Faculty getFromDb = entityManager.persist(savedInDb);
        assertEquals(savedInDb, getFromDb);
    }

    @Test
    void findByIdhouldExecuteOnceWhenDbCallFineAndRweturnAuditorium() {
        Faculty faculty = new Faculty("Faculty");
        Faculty savedInDb = entityManager.persist(faculty);
        Faculty getFromDb = facultyDao.findById(savedInDb.getId()).get();
        assertEquals(savedInDb, getFromDb);
    }

    @Test
    void findAllShouldReturnListOfAuditoriumIfAtLeastOneExists() {
        Faculty faculty = new Faculty("Faculty");
        Faculty facultyTwo = new Faculty("OtherFaculty");
        Faculty savedInDb = entityManager.persist(faculty);
        Faculty savedInDbTwo = entityManager.persist(facultyTwo);
        List<Faculty> facultysFromDb = (List<Faculty>) facultyDao.findAll();
        List<Faculty> facultysSaveInDb = Arrays.asList(savedInDb, savedInDbTwo);
        assertEquals(facultysFromDb, facultysSaveInDb);
    }

    @Test
    void delteteShouldExecuteOnceWhenDbCallFine() {
        Faculty faculty = new Faculty("Faculty");
        Faculty facultyTwo = new Faculty("OtherFaculty");
        Faculty savedInDb = entityManager.persist(faculty);
        Faculty savedInDbTwo = entityManager.persist(facultyTwo);
        entityManager.remove(savedInDb);
        List<Faculty> facultysFromDb = (List<Faculty>) facultyDao.findAll();
        List<Faculty> facultys = Arrays.asList(savedInDbTwo);
        assertEquals(facultysFromDb, facultys);
    }

    @Test
    void updateShouldExecuteOnceWhenDbCallFineAndUodateEntityField() {
        Faculty faculty = new Faculty("Faculty");
        entityManager.persist(faculty);
        Faculty getFromDb = facultyDao.findById(faculty.getId()).get();
        String actual = "New";
        getFromDb.setName(actual);
        entityManager.persist(getFromDb);
        String expected = getFromDb.getName();
        assertEquals(expected, actual);
    }

}
