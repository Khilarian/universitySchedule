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
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @MockBean
    private StudentDao mockStudentDao;

    @MockBean
    private GroupService mockGroupService;
    
    @MockBean
    private UserService mockUserService;

    @Test
    void addShouldExecuteOnceWhenDbCallFine() {
        Faculty faculty = new Faculty(1, "First");
        Group group = new Group(1, "AA-01", faculty);
        Student savedStudent = new Student(0, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", group);
        Student expectedStudent = new Student(1, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", group);
        User user = User.fromPerson(savedStudent);
        Mockito.when(mockStudentDao.save(savedStudent)).thenReturn(expectedStudent);
        Mockito.when(mockUserService.add(any())).thenReturn(user);
        assertEquals(studentService.add(savedStudent), expectedStudent);
        Mockito.verify(mockStudentDao, times(1)).save(savedStudent);
        Mockito.verify(mockUserService, times(1)).add(user);
    }

    @Test
    void addShouldRaiseExceptionIfIdNotEqualZero() {
        Faculty faculty = new Faculty(1, "First");
        Group group = new Group(1, "AA-01", faculty);
        Student savedStudent = new Student(1, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", group);
        assertThrows(InvalidEntityException.class, () -> studentService.add(savedStudent));
    }

    @Test
    void findByIdShouldExecuteOnceWhenDbCallFineAndReturnStudent() {
        Faculty faculty = new Faculty(1, "First");
        Group group = new Group(1, "AA-01", faculty);
        Student expectedStudent = new Student(1, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", group);
        Mockito.when(mockStudentDao.findById(1)).thenReturn(Optional.of(expectedStudent));
        assertEquals(studentService.findById(1), expectedStudent);
        Mockito.verify(mockStudentDao, times(1)).findById(1);
    }

    @Test
    void findByIdShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> studentService.findById(1));
        Mockito.verify(mockStudentDao, times(1)).findById(1);
    }

    @Test
    void findByEmailShouldExecuteOnceWhenDbCallFineAndReturnStudent() {
        Faculty faculty = new Faculty(1, "First");
        Group group = new Group(1, "AA-01", faculty);
        Student expected = new Student(1, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", group);
        Mockito.when(mockStudentDao.findByPhone(Mockito.anyString())).thenReturn(Optional.of(expected));
        assertEquals(studentService.findByPhone("+7(123)4567890"), expected);
        Mockito.verify(mockStudentDao, times(1)).findByPhone("+7(123)4567890");
    }

    @Test
    void findByPhoneShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> studentService.findByPhone("+7(123)4567890"));
        Mockito.verify(mockStudentDao, times(1)).findByPhone("+7(123)4567890");
    }

    @Test
    void findByPhoneShouldExecuteOnceWhenDbCallFineAndReturnStudent() {
        Faculty faculty = new Faculty(1, "First");
        Group group = new Group(1, "AA-01", faculty);
        Student expected = new Student(1, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", group);
        Mockito.when(mockStudentDao.findByEmail(Mockito.anyString())).thenReturn(Optional.of(expected));
        assertEquals(studentService.findByEmail("mj@rs.com"), expected);
        Mockito.verify(mockStudentDao, times(1)).findByEmail("mj@rs.com");
    }

    @Test
    void findByEmailShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> studentService.findByEmail("aaa"));
        Mockito.verify(mockStudentDao, times(1)).findByEmail("aaa");
    }

    @Test
    void findAllShouldReturnListOfAuditoriumIfAtLeastOneExists() {
        Faculty faculty = new Faculty(1, "First");
        Group group = new Group(1, "AA-01", faculty);
        Student student = new Student(1, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", group);
        Student studentTwo = new Student(2, "Bill", "Goldberg", "bg@wwe.com", "+7(123)4567891", group);
        List<Student> students = Arrays.asList(student, studentTwo);
        Mockito.when(mockStudentDao.findAll()).thenReturn(students);
        assertEquals(studentService.findAll(), students);
        Mockito.verify(mockStudentDao, times(1)).findAll();
    }

    @Test
    void deleteShouldExecuteOnceWhenDbCallFine() {
        Faculty faculty = new Faculty(1, "First");
        Group group = new Group(1, "AA-01", faculty);
        Student student = new Student(1, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", group);
        Mockito.when(mockStudentDao.findById(any())).thenReturn(Optional.of(student));
        studentService.deleteById(student.getId());
        Mockito.verify(mockStudentDao, times(1)).findById(1);
        Mockito.verify(mockStudentDao, times(1)).deleteById(1);
        Mockito.verify(mockUserService, times(1)).deleteByEmail(student.getEmail());
    }

    @Test
    void updateShouldExecuteOnceWhenDbCallFineAndUpdateEntityField() {
        Faculty faculty = new Faculty(1, "First");
        Group group = new Group(1, "AA-01", faculty);
        Student updatedStudent = new Student(1, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", group);
        User user = User.fromPerson(updatedStudent);
        Mockito.when(mockStudentDao.findById(1)).thenReturn(Optional.of(updatedStudent));
        Mockito.when(mockUserService.update(any())).thenReturn(user);
        Mockito.when(mockStudentDao.save(updatedStudent)).thenReturn(updatedStudent);
        assertEquals(studentService.update(updatedStudent), updatedStudent);
        Mockito.verify(mockStudentDao, times(1)).save(updatedStudent);
        Mockito.verify(mockUserService, times(1)).update(user);
    }

    @Test
    void updateShouldRaiseExceptionIfIdEqualZero() {
        Faculty faculty = new Faculty(1, "First");
        Group group = new Group(1, "AA-01", faculty);
        Student updatedStudent = new Student(0, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", group);
        assertThrows(InvalidEntityException.class, () -> studentService.update(updatedStudent));
    }

    @Test
    void getGroupsShouldReturnListOfGroupsIfAatLeastOneExist() {
        Faculty faculty = new Faculty(1, "First");
        Group group = new Group(1, "AA-01", faculty);
        Group groupTwo = new Group(2, "AA-02", faculty);
        List<Group> groups = Arrays.asList(group, groupTwo);
        Mockito.when(mockGroupService.findAll()).thenReturn(groups);
        assertEquals(studentService.getGroups(), groups);
        Mockito.verify(mockGroupService, times(1)).findAll();
    }

}
