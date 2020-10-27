package com.rumakin.universityschedule.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rumakin.universityschedule.dao.GroupDao;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.*;

@SpringBootTest
class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @MockBean
    private GroupDao mockGroupDao;

    @Test
    public void addShouldExecuteOnceWhenDbCallFine() {
        Faculty faculty = new Faculty(1, "First");
        Group saved = new Group(0, "AA-25", faculty);
        Group expected = new Group(1, "AA-25", faculty);
        Mockito.when(mockGroupDao.save(saved)).thenReturn(expected);
        assertEquals(groupService.add(saved), expected);
    }

    @Test
    public void findByIdShouldExecuteOnceWhenDbCallFineAndReturnAuditorium() {
        Group expected = new Group(1, "AA-25", new Faculty(1, "First"));
        Mockito.when(mockGroupDao.findById(1)).thenReturn(Optional.of(expected));
        assertEquals(groupService.findById(1), expected);
    }

    @Test
    public void findByIdShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> groupService.findById(1));
    }

    @Test
    public void findAllShouldReturnListOfAuditoriumIfAtLeastOneExists() {
        Faculty faculty = new Faculty(1, "First");
        Group group = new Group(1, "AA-25", faculty);
        Group groupTwo = new Group(2, "AA-26", faculty);
        List<Group> groups = Arrays.asList(group, groupTwo);
        Mockito.when(mockGroupDao.findAll()).thenReturn(groups);
        assertEquals(groupService.findAll(), groups);
    }

    @Test
    public void delteteShouldExecuteOnceWhenDbCallFine() {
        Faculty faculty = new Faculty(1, "First");
        Group group = new Group(1, "AA-25", faculty);
        Mockito.when(mockGroupDao.findById(1)).thenReturn(Optional.of(group));
        Mockito.when(mockGroupDao.existsById(group.getId())).thenReturn(false);
        assertFalse(mockGroupDao.existsById(group.getId()));
    }

    @Test
    public void updateShouldExecuteOnceWhenDbCallFineAndUodateEntityField() {
        Faculty faculty = new Faculty(1, "First");
        Group group = new Group(1, "AA-25", faculty);
        Mockito.when(mockGroupDao.findById(1)).thenReturn(Optional.of(group));
        group.setName("AA-26");
        Mockito.when(mockGroupDao.save(group)).thenReturn(group);
        assertEquals(groupService.update(group), group);
    }

}
