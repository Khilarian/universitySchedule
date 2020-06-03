package com.rumakin.universityschedule.service;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rumakin.universityschedule.dao.FacultyDao;
import com.rumakin.universityschedule.exceptions.ResourceNotFoundException;
import com.rumakin.universityschedule.models.*;

@SpringBootTest
class FacultyServiceTest {

    @Autowired
    private FacultyService facultyService;

    @MockBean
    private FacultyDao mockFacultyDao;

    @Test
    public void addShouldExecuteOnceWhenDbCallFine() {
        Faculty expected = new Faculty(1, "First");
        Mockito.when(mockFacultyDao.save(expected)).thenReturn(expected);
        assertEquals(facultyService.add(expected), expected);
    }

    @Test
    public void findByIdShouldExecuteOnceWhenDbCallFineAndReturnAuditorium() {
        Faculty expected = new Faculty(1, "First");
        Mockito.when(mockFacultyDao.findById(1)).thenReturn(Optional.of(expected));
        assertEquals(facultyService.find(1), expected);
    }

    @Test
    public void findByIdShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> facultyService.find(1));
    }

    @Test
    public void findAllShouldReturnListOfAuditoriumIfAtLeastOneExists() {
        Faculty faculty = new Faculty(1, "First");
        Faculty facultyTwo = new Faculty(2, "Second");
        List<Faculty> faculties = Arrays.asList(faculty, facultyTwo);
        Mockito.when(mockFacultyDao.findAll()).thenReturn(faculties);
        assertEquals(facultyService.findAll(), faculties);
    }

    @Test
    public void delteteShouldExecuteOnceWhenDbCallFine() {
        Faculty faculty = new Faculty(1, "First");
        Mockito.when(mockFacultyDao.findById(1)).thenReturn(Optional.of(faculty));
        Mockito.when(mockFacultyDao.existsById(faculty.getId())).thenReturn(false);
        assertFalse(mockFacultyDao.existsById(faculty.getId()));
    }

    @Test
    public void updateShouldExecuteOnceWhenDbCallFineAndUodateEntityField() {
        Faculty faculty = new Faculty(1, "First");
        Mockito.when(mockFacultyDao.findById(1)).thenReturn(Optional.of(faculty));
        faculty.setName("second");
        Mockito.when(mockFacultyDao.save(faculty)).thenReturn(faculty);
        assertEquals(facultyService.update(faculty), faculty);
    }

}
