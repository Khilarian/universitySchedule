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
class TeacherServiceTest {

    @Autowired
    private TeacherService teacherService;

    @MockBean
    private TeacherDao mockTeacherDao;

    @MockBean
    private FacultyService mockFacultyService;

    @MockBean
    private CourseService mockCourseService;

    @Test
    void addShouldExecuteOnceWhenDbCallFine() {
        Faculty faculty = new Faculty(1, "First");
        Teacher savedTeacher = new Teacher(0, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", faculty);
        Teacher expectedTeacher = new Teacher(1, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", faculty);
        Mockito.when(mockTeacherDao.save(savedTeacher)).thenReturn(expectedTeacher);
        assertEquals(teacherService.add(savedTeacher), expectedTeacher);
        Mockito.verify(mockTeacherDao, times(1)).save(savedTeacher);
    }

    @Test
    void addShouldRaiseExceptionIfIdNotEqualZero() {
        Faculty faculty = new Faculty(1, "First");
        Teacher savedTeacher = new Teacher(1, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", faculty);
        assertThrows(InvalidEntityException.class, () -> teacherService.add(savedTeacher));
    }

    @Test
    void findByIdShouldExecuteOnceWhenDbCallFineAndReturnTeacher() {
        Faculty faculty = new Faculty(1, "First");
        Teacher expectedTeacher = new Teacher(1, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", faculty);
        Mockito.when(mockTeacherDao.findById(1)).thenReturn(Optional.of(expectedTeacher));
        assertEquals(teacherService.findById(1), expectedTeacher);
        Mockito.verify(mockTeacherDao, times(1)).findById(1);
    }

    @Test
    void findByIdShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> teacherService.findById(1));
        Mockito.verify(mockTeacherDao, times(1)).findById(1);
    }

    @Test
    void findByEmailShouldExecuteOnceWhenDbCallFineAndReturnTeacher() {
        Faculty faculty = new Faculty(1, "First");
        Teacher expected = new Teacher(1, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", faculty);
        Mockito.when(mockTeacherDao.findByPhone(Mockito.anyString())).thenReturn(Optional.of(expected));
        assertEquals(teacherService.findByPhone("+7(123)4567890"), expected);
        Mockito.verify(mockTeacherDao, times(1)).findByPhone("+7(123)4567890");
    }

    @Test
    void findByPhoneShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> teacherService.findByPhone("+7(123)4567890"));
        Mockito.verify(mockTeacherDao, times(1)).findByPhone("+7(123)4567890");
    }

    @Test
    void findByPhoneShouldExecuteOnceWhenDbCallFineAndReturnTeacher() {
        Faculty faculty = new Faculty(1, "First");
        Teacher expected = new Teacher(1, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", faculty);
        Mockito.when(mockTeacherDao.findByEmail(Mockito.anyString())).thenReturn(Optional.of(expected));
        assertEquals(teacherService.findByEmail("mj@rs.com"), expected);
        Mockito.verify(mockTeacherDao, times(1)).findByEmail("mj@rs.com");
    }

    @Test
    void findByEmailShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> teacherService.findByEmail("aaa"));
        Mockito.verify(mockTeacherDao, times(1)).findByEmail("aaa");
    }

    @Test
    void findAllShouldReturnListOfAuditoriumIfAtLeastOneExists() {
        Faculty faculty = new Faculty(1, "First");
        Teacher teacher = new Teacher(1, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", faculty);
        Teacher teacherTwo = new Teacher(2, "Bill", "Goldberg", "bg@wwe.com", "+7(123)4567891", faculty);
        List<Teacher> teachers = Arrays.asList(teacher, teacherTwo);
        Mockito.when(mockTeacherDao.findAll()).thenReturn(teachers);
        assertEquals(teacherService.findAll(), teachers);
        Mockito.verify(mockTeacherDao, times(1)).findAll();
    }

    @Test
    void deleteShouldExecuteOnceWhenDbCallFine() {
        Faculty faculty = new Faculty(1, "First");
        Teacher teacher = new Teacher(1, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", faculty);
        teacherService.deleteById(teacher.getId());
        Mockito.verify(mockTeacherDao, times(1)).deleteById(1);
    }

    @Test
    void updateShouldExecuteOnceWhenDbCallFineAndUpdateEntityField() {
        Faculty faculty = new Faculty(1, "First");
        Teacher updatedTeacher = new Teacher(1, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", faculty);
        Mockito.when(mockTeacherDao.findById(1)).thenReturn(Optional.of(updatedTeacher));
        Mockito.when(mockTeacherDao.save(updatedTeacher)).thenReturn(updatedTeacher);
        assertEquals(teacherService.update(updatedTeacher), updatedTeacher);
        Mockito.verify(mockTeacherDao, times(1)).save(updatedTeacher);
    }

    @Test
    void updateShouldRaiseExceptionIfIdEqualZero() {
        Faculty faculty = new Faculty(1, "First");
        Teacher updatedTeacher = new Teacher(0, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", faculty);
        assertThrows(InvalidEntityException.class, () -> teacherService.update(updatedTeacher));
    }

    @Test
    void getFacultiesShouldReturnListOfFacultiesIfAatLeastOneExist() {
        Faculty faculty = new Faculty(1, "First");
        Faculty facultyTwo = new Faculty(2, "Second");
        List<Faculty> faculties = Arrays.asList(faculty, facultyTwo);
        Mockito.when(mockFacultyService.findAll()).thenReturn(faculties);
        assertEquals(teacherService.getFaculties(), faculties);
        Mockito.verify(mockFacultyService, times(1)).findAll();
    }

    @Test
    void getCoursesShouldReturnListOfCoursesIfAatLeastOneExist() {
        Faculty faculty = new Faculty(1, "First");
        Course course = new Course(1, "Course", faculty);
        Course courseTwo = new Course(2, "CourseTwo", faculty);
        List<Course> courses = Arrays.asList(course, courseTwo);
        Mockito.when(mockCourseService.findAll()).thenReturn(courses);
        assertEquals(teacherService.getCourses(), courses);
        Mockito.verify(mockCourseService, times(1)).findAll();
    }

}
