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
import com.rumakin.universityschedule.exception.*;
import com.rumakin.universityschedule.model.*;

@SpringBootTest
class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @MockBean
    private CourseDao mockCourseDao;
    
    @MockBean
    private FacultyService mockFacultyService;

    @Test
    public void addShouldExecuteOnceWhenDbCallFine() {
        Faculty faculty = new Faculty(1, "First");
        Course savedCourse = new Course(0, "Math", faculty);
        Course expectedCourse = new Course(1, "Math", faculty);
        Mockito.when(mockCourseDao.save(savedCourse)).thenReturn(expectedCourse);
        assertEquals(courseService.add(savedCourse), expectedCourse);
        Mockito.verify(mockCourseDao, times(1)).save(savedCourse);
    }
    
    @Test
    public void addShouldRaiseExceptionIfIdNotEqualZero() {
        Faculty faculty = new Faculty(1, "First");
        Course savedCourse = new Course(1, "IT", faculty);
        assertThrows(InvalidEntityException.class, () -> courseService.add(savedCourse));
    }

    @Test
    public void findByIdShouldExecuteOnceWhenDbCallFineAndReturnCourse() {
        Course expected = new Course(1, "Math", new Faculty(1, "First"));
        Mockito.when(mockCourseDao.findById(1)).thenReturn(Optional.of(expected));
        assertEquals(courseService.findById(1), expected);
        Mockito.verify(mockCourseDao, times(1)).findById(1);
    }

    @Test
    public void findByIdShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> courseService.findById(1));
        Mockito.verify(mockCourseDao, times(1)).findById(1);
    }
    
    @Test
    public void findByNameShouldExecuteOnceWhenDbCallFineAndReturnCourse() {
        Course expected = new Course(1, "Math", new Faculty(1, "First"));
        Mockito.when(mockCourseDao.findByName(Mockito.anyString())).thenReturn(Optional.of(expected));
        assertEquals(courseService.findByName("Math"), expected);
        Mockito.verify(mockCourseDao, times(1)).findByName("Math");
    }

    @Test
    public void findByNameShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> courseService.findByName("aaa"));
        Mockito.verify(mockCourseDao, times(1)).findByName("aaa");
    }

    @Test
    public void findAllShouldReturnListOfAuditoriumIfAtLeastOneExists() {
        Faculty faculty = new Faculty(1, "First");
        Course course = new Course(1, "Math", faculty);
        Course courseTwo = new Course(2, "Computer Science", faculty);
        List<Course> courses = Arrays.asList(course, courseTwo);
        Mockito.when(mockCourseDao.findAll()).thenReturn(courses);
        assertEquals(courseService.findAll(), courses);
        Mockito.verify(mockCourseDao, times(1)).findAll();
    }

    @Test
    public void deleteShouldExecuteOnceWhenDbCallFine() {
        Faculty faculty = new Faculty(1, "First");
        Course course = new Course(1, "Math", faculty);
        courseService.deleteById(course.getId());
        Mockito.verify(mockCourseDao, times(1)).deleteById(1);
    }

    @Test
    public void updateShouldExecuteOnceWhenDbCallFineAndUpdateEntityField() {
        Faculty faculty = new Faculty(1, "First");
        Course updatedCourse = new Course(1, "Math", faculty);
        Mockito.when(mockCourseDao.findById(1)).thenReturn(Optional.of(updatedCourse));
        updatedCourse.setName("Computer Science");
        Mockito.when(mockCourseDao.save(updatedCourse)).thenReturn(updatedCourse);
        assertEquals(courseService.update(updatedCourse), updatedCourse);
        Mockito.verify(mockCourseDao, times(1)).save(updatedCourse);
    }
    
    @Test
    public void updateShouldRaiseExceptionIfIdEqualZero() {
        Faculty faculty = new Faculty(1, "First");
        Course updatedCourse = new Course(0, "IT", faculty);
        assertThrows(InvalidEntityException.class, () -> courseService.update(updatedCourse));
    }
    
    @Test
    public void getFacultiesShouldReturnAllFacultieFromDB() {
        Faculty faculty = new Faculty(1, "First");
        Faculty facultyTwo = new Faculty(2, "Second");
        List<Faculty> faculties = Arrays.asList(faculty,facultyTwo);
        Mockito.when(mockFacultyService.findAll()).thenReturn(faculties);
        assertEquals(courseService.getFaculties(), faculties);
        Mockito.verify(mockFacultyService, times(1)).findAll();
    }

}
