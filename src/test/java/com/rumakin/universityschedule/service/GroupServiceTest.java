package com.rumakin.universityschedule.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.*;

import com.rumakin.universityschedule.dao.*;
import com.rumakin.universityschedule.exception.InvalidEntityException;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.*;

@SpringBootTest
class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @MockBean
    private GroupDao mockGroupDao;
    
    @MockBean
    private FacultyService mockFacultyService;

    @Test
    public void addShouldExecuteOnceWhenDbCallFine() {
        Faculty faculty = new Faculty(1, "First");
        Group savedGroup = new Group(0, "AA-25", faculty);
        Group expectedGroup = new Group(1, "AA-25", faculty);
        Mockito.when(mockGroupDao.save(savedGroup)).thenReturn(expectedGroup);
        assertEquals(groupService.add(savedGroup), expectedGroup);
        Mockito.verify(mockGroupDao, times(1)).save(savedGroup);
    }
    
    @Test
    public void addShouldRaiseExceptionIfIdNotEqualZero() {
        Faculty faculty = new Faculty(1, "First");
        Group savedGroup = new Group(1, "AA-25", faculty);
        assertThrows(InvalidEntityException.class, () -> groupService.add(savedGroup));
    }

    @Test
    public void findByIdShouldExecuteOnceWhenDbCallFineAndReturnGroup() {
        Group expectedGroup = new Group(1, "AA-25", new Faculty(1, "First"));
        Mockito.when(mockGroupDao.findById(1)).thenReturn(Optional.of(expectedGroup));
        assertEquals(groupService.findById(1), expectedGroup);
        Mockito.verify(mockGroupDao, times(1)).findById(1);
    }

    @Test
    public void findByIdShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> groupService.findById(1));
        Mockito.verify(mockGroupDao, times(1)).findById(1);
    }
    
    @Test
    public void findByNameShouldExecuteOnceWhenDbCallFineAndReturnGroup() {
        Group expected = new Group(1, "AA-01", new Faculty(1, "First"));
        Mockito.when(mockGroupDao.findByName(Mockito.anyString())).thenReturn(Optional.of(expected));
        assertEquals(groupService.findByName("AA-01"), expected);
        Mockito.verify(mockGroupDao, times(1)).findByName("AA-01");
    }

    @Test
    public void findByNameShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> groupService.findByName("aaa"));
        Mockito.verify(mockGroupDao, times(1)).findByName("aaa");
    }

    @Test
    public void findAllShouldReturnListOfAuditoriumIfAtLeastOneExists() {
        Faculty faculty = new Faculty(1, "First");
        Group group = new Group(1, "AA-25", faculty);
        Group groupTwo = new Group(2, "AA-26", faculty);
        List<Group> groups = Arrays.asList(group, groupTwo);
        Mockito.when(mockGroupDao.findAll()).thenReturn(groups);
        assertEquals(groupService.findAll(), groups);
        Mockito.verify(mockGroupDao, times(1)).findAll();
    }

    @Test
    public void deleteShouldExecuteOnceWhenDbCallFine() {
        Faculty faculty = new Faculty(1, "First");
        Group group = new Group(1, "AA-25", faculty);
        groupService.deleteById(group.getId());
        Mockito.verify(mockGroupDao, times(1)).deleteById(1);
    }

    @Test
    public void updateShouldExecuteOnceWhenDbCallFineAndUpdateEntityField() {
        Faculty faculty = new Faculty(1, "First");
        Group updatedGroup = new Group(1, "AA-25", faculty);
        Mockito.when(mockGroupDao.findById(1)).thenReturn(Optional.of(updatedGroup));
        updatedGroup.setName("AA-26");
        Mockito.when(mockGroupDao.save(updatedGroup)).thenReturn(updatedGroup);
        assertEquals(groupService.update(updatedGroup), updatedGroup);
        Mockito.verify(mockGroupDao, times(1)).save(updatedGroup);
    }
    
    @Test
    public void updateShouldRaiseExceptionIfIdEqualZero() {
        Faculty faculty = new Faculty(1, "First");
        Group updatedGroup = new Group(0, "AA-25", faculty);
        assertThrows(InvalidEntityException.class, () -> groupService.update(updatedGroup));
    }
    
    @Test
    public void getFacultiesShouldReturnAllFacultiesFromDB() {
        Faculty faculty = new Faculty(1, "First");
        Faculty facultyTwo = new Faculty(2, "Second");
        List<Faculty> faculties = Arrays.asList(faculty,facultyTwo);
        Mockito.when(mockFacultyService.findAll()).thenReturn(faculties);
        assertEquals(groupService.getFaculties(), faculties);
        Mockito.verify(mockFacultyService, times(1)).findAll();
    }

}
