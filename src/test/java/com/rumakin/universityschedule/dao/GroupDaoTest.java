package com.rumakin.universityschedule.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import com.rumakin.universityschedule.model.*;

@DataJpaTest
class GroupDaoTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private GroupDao groupDao;

    @Test
    public void addShouldExecuteOnceWhenDbCallFine() {
        Faculty faculty = new Faculty("Faculty");
        Group savedInDb = new Group("AA_35", faculty);
        Group getFromDb = entityManager.persist(savedInDb);
        assertEquals(savedInDb, getFromDb);
    }

    @Test
    public void findByIdhouldExecuteOnceWhenDbCallFineAndRweturnGroup() {
        Faculty faculty = new Faculty("Faculty");
        Group group = new Group("AA_35", faculty);
        Group savedInDb = entityManager.persist(group);
        Group getFromDb = groupDao.findById(savedInDb.getId()).get();
        assertEquals(savedInDb, getFromDb);
    }

    @Test
    public void findAllShouldReturnListOfGroupIfAtLeastOneExists() {
        Faculty faculty = new Faculty("Faculty");
        faculty = entityManager.persist(faculty);
        Group group = new Group("AA_35", faculty);
        Group groupTwo = new Group("AA_36", faculty);
        Group savedInDb = entityManager.persist(group);
        Group savedInDbTwo = entityManager.persist(groupTwo);
        List<Group> groupsFromDb = (List<Group>) groupDao.findAll();
        List<Group> groupsSaveInDb = Arrays.asList(savedInDb, savedInDbTwo);
        assertEquals(groupsFromDb, groupsSaveInDb);
    }

    @Test
    public void delteteShouldExecuteOnceWhenDbCallFine() {
        Faculty faculty = new Faculty("Faculty");
        faculty = entityManager.persist(faculty);
        Group group = new Group("AA_35", faculty);
        Group groupTwo = new Group("AA_36", faculty);
        Group savedInDb = entityManager.persist(group);
        Group savedInDbTwo = entityManager.persist(groupTwo);
        entityManager.remove(savedInDb);
        List<Group> groupsFromDb = (List<Group>) groupDao.findAll();
        List<Group> groups = Arrays.asList(savedInDbTwo);
        assertEquals(groupsFromDb, groups);
    }

    @Test
    public void updateShouldExecuteOnceWhenDbCallFineAndUodateEntityField() {
        Faculty faculty = new Faculty("Faculty");
        Group group = new Group("AA_35", faculty);
        entityManager.persist(group);
        Group getFromDb = groupDao.findById(group.getId()).get();
        String actual = "AA_36";
        getFromDb.setName(actual);
        entityManager.persist(getFromDb);
        String expected = getFromDb.getName();
        assertEquals(expected, actual);
    }

}
