package com.rumakin.universityschedule.service;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rumakin.universityschedule.dao.FacultyDao;
import com.rumakin.universityschedule.exception.InvalidEntityException;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.*;

@SpringBootTest
class FacultyServiceTest {

    @Autowired
    private FacultyService facultyService;

    @MockBean
    private FacultyDao mockFacultyDao;

    @Test
    public void addShouldExecuteOnceWhenDbCallFine() {
        Faculty savedFaculty = new Faculty(0, "First");
        Faculty expectedFaculty = new Faculty(1, "First");
        Mockito.when(mockFacultyDao.save(savedFaculty)).thenReturn(expectedFaculty);
        assertEquals(facultyService.add(savedFaculty), expectedFaculty);
        Mockito.verify(mockFacultyDao, times(1)).save(savedFaculty);
    }
    
    @Test
    public void addShouldRaiseExceptionIfIdNotEqualZero() {
        Faculty savedFaculty = new Faculty(1, "First");
        assertThrows(InvalidEntityException.class, () -> facultyService.add(savedFaculty));
    }

    @Test
    public void findByIdShouldExecuteOnceWhenDbCallFineAndReturnFaculty() {
        Faculty expectedFaculty = new Faculty(1, "First");
        Mockito.when(mockFacultyDao.findById(1)).thenReturn(Optional.of(expectedFaculty));
        assertEquals(facultyService.findById(1), expectedFaculty);
        Mockito.verify(mockFacultyDao, times(1)).findById(1);
    }

    @Test
    public void findByIdShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> facultyService.findById(1));
        Mockito.verify(mockFacultyDao, times(1)).findById(1);
    }

    @Test
    public void findByNameShouldExecuteOnceWhenDbCallFineAndReturnFaculty() {
        Faculty expectedFaculty = new Faculty(1, "First");
        Mockito.when(mockFacultyDao.findByName("First")).thenReturn(Optional.of(expectedFaculty));
        assertEquals(facultyService.findByName("First"), expectedFaculty);
        Mockito.verify(mockFacultyDao, times(1)).findByName("First");
    }

    @Test
    public void findByNameShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> facultyService.findByName("Any"));
        Mockito.verify(mockFacultyDao, times(1)).findByName("Any");
    }

    @Test
    public void findAllShouldReturnListOfAuditoriumIfAtLeastOneExists() {
        Faculty faculty = new Faculty(1, "First");
        Faculty facultyTwo = new Faculty(2, "Second");
        List<Faculty> faculties = Arrays.asList(faculty, facultyTwo);
        Mockito.when(mockFacultyDao.findAll()).thenReturn(faculties);
        assertEquals(facultyService.findAll(), faculties);
        Mockito.verify(mockFacultyDao, times(1)).findAll();
    }

    @Test
    public void deleteShouldExecuteOnceWhenDbCallFine() {
        Faculty faculty = new Faculty(1, "First");
        facultyService.deleteById(faculty.getId());
        verify(mockFacultyDao, times(1)).deleteById(1);
    }

    @Test
    public void updateShouldExecuteOnceWhenDbCallFineAndUpdateEntityField() {
        Faculty faculty = new Faculty(1, "First");
        Mockito.when(mockFacultyDao.findById(1)).thenReturn(Optional.of(faculty));
        faculty.setName("second");
        Mockito.when(mockFacultyDao.save(faculty)).thenReturn(faculty);
        assertEquals(facultyService.update(faculty), faculty);
        Mockito.verify(mockFacultyDao, times(1)).save(faculty);
    }
    
    @Test
    public void updateShouldRaiseExceptionIfIdEqualZero() {
        Faculty updatedFaculty = new Faculty(0, "First");
        assertThrows(InvalidEntityException.class, () -> facultyService.update(updatedFaculty));
    }

}
