package com.rumakin.universityschedule.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rumakin.universityschedule.dao.CourseDao;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.*;

@SpringBootTest
class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @MockBean
    private CourseDao mockCourseDao;

    @Test
    public void addShouldExecuteOnceWhenDbCallFine() {
        Faculty faculty = new Faculty(1, "First");
        Course saved = new Course(0, "Math", faculty);
        Course expected = new Course(1, "Math", faculty);
        Mockito.when(mockCourseDao.save(saved)).thenReturn(expected);
        assertEquals(courseService.add(saved), expected);
    }

    @Test
    public void findByIdShouldExecuteOnceWhenDbCallFineAndReturnAuditorium() {
        Course expected = new Course(1, "Math", new Faculty(1, "First"));
        Mockito.when(mockCourseDao.findById(1)).thenReturn(Optional.of(expected));
        assertEquals(courseService.findById(1), expected);
    }

    @Test
    public void findByIdShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> courseService.findById(1));
    }

    @Test
    public void findAllShouldReturnListOfAuditoriumIfAtLeastOneExists() {
        Faculty faculty = new Faculty(1, "First");
        Course course = new Course(1, "Math", faculty);
        Course courseTwo = new Course(2, "Computer Science", faculty);
        List<Course> courses = Arrays.asList(course, courseTwo);
        Mockito.when(mockCourseDao.findAll()).thenReturn(courses);
        assertEquals(courseService.findAll(), courses);
    }

    @Test
    public void delteteShouldExecuteOnceWhenDbCallFine() {
        Faculty faculty = new Faculty(1, "First");
        Course course = new Course(1, "Math", faculty);
        Mockito.when(mockCourseDao.findById(1)).thenReturn(Optional.of(course));
        Mockito.when(mockCourseDao.existsById(course.getId())).thenReturn(false);
        assertFalse(mockCourseDao.existsById(course.getId()));
    }

    @Test
    public void updateShouldExecuteOnceWhenDbCallFineAndUodateEntityField() {
        Faculty faculty = new Faculty(1, "First");
        Course course = new Course(1, "Math", faculty);
        Mockito.when(mockCourseDao.findById(1)).thenReturn(Optional.of(course));
        course.setName("Computer Science");
        Mockito.when(mockCourseDao.save(course)).thenReturn(course);
        assertEquals(courseService.update(course), course);
    }

}
