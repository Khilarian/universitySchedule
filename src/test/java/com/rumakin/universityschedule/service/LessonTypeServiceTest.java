package com.rumakin.universityschedule.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rumakin.universityschedule.dao.*;
import com.rumakin.universityschedule.exception.*;
import com.rumakin.universityschedule.model.LessonType;

@SpringBootTest
class LessonTypeServiceTest {

    @Autowired
    private LessonTypeService lessonTypeService;

    @MockBean
    private LessonTypeDao mockLessonTypeDao;

    @Test
    void addShouldExecuteOnceWhenDbCallFine() {
        LessonType savedLessonType = new LessonType(0, "EXAM");
        LessonType expectedLessonType = new LessonType(1, "EXAM");
        Mockito.when(mockLessonTypeDao.save(savedLessonType)).thenReturn(expectedLessonType);
        assertEquals(lessonTypeService.add(savedLessonType), expectedLessonType);
        Mockito.verify(mockLessonTypeDao, times(1)).save(savedLessonType);
    }

    @Test
    void addShouldRaiseExceptionIfIdNotEqualZero() {
        LessonType savedLessonType = new LessonType(1, "EXAM");
        assertThrows(InvalidEntityException.class, () -> lessonTypeService.add(savedLessonType));
    }

    @Test
    void findByIdShouldExecuteOnceWhenDbCallFineAndReturnLessonType() {
        LessonType expectedLessonType = new LessonType(1, "EXAM");
        Mockito.when(mockLessonTypeDao.findById(1)).thenReturn(Optional.of(expectedLessonType));
        assertEquals(lessonTypeService.findById(1), expectedLessonType);
        Mockito.verify(mockLessonTypeDao, times(1)).findById(1);
    }

    @Test
    void findByIdShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> lessonTypeService.findById(1));
        Mockito.verify(mockLessonTypeDao, times(1)).findById(1);
    }

    @Test
    void findByNameShouldExecuteOnceWhenDbCallFineAndReturnLessonType() {
        LessonType expected = new LessonType(1, "AA-01");
        Mockito.when(mockLessonTypeDao.findByName(Mockito.anyString())).thenReturn(Optional.of(expected));
        assertEquals(lessonTypeService.findByName("AA-01"), expected);
        Mockito.verify(mockLessonTypeDao, times(1)).findByName("AA-01");
    }

    @Test
    void findByNameShouldRaiseExceptionIfIdMissed() {
        assertThrows(ResourceNotFoundException.class, () -> lessonTypeService.findByName("aaa"));
        Mockito.verify(mockLessonTypeDao, times(1)).findByName("aaa");
    }

    @Test
    void findAllShouldReturnListOfAuditoriumIfAtLeastOneExists() {
        LessonType lessonType = new LessonType(1, "EXAM");
        LessonType lessonTypeTwo = new LessonType(2, "LECTURE");
        List<LessonType> lessonTypes = Arrays.asList(lessonType, lessonTypeTwo);
        Mockito.when(mockLessonTypeDao.findAll()).thenReturn(lessonTypes);
        assertEquals(lessonTypeService.findAll(), lessonTypes);
        Mockito.verify(mockLessonTypeDao, times(1)).findAll();
    }

    @Test
    void deleteShouldExecuteOnceWhenDbCallFine() {
        LessonType lessonType = new LessonType(1, "EXAM");
        lessonTypeService.deleteById(lessonType.getId());
        Mockito.verify(mockLessonTypeDao, times(1)).deleteById(1);
    }

    @Test
    void updateShouldExecuteOnceWhenDbCallFineAndUpdateEntityField() {
        LessonType updatedLessonType = new LessonType(1, "EXAM");
        Mockito.when(mockLessonTypeDao.findById(1)).thenReturn(Optional.of(updatedLessonType));
        Mockito.when(mockLessonTypeDao.save(updatedLessonType)).thenReturn(updatedLessonType);
        assertEquals(lessonTypeService.update(updatedLessonType), updatedLessonType);
        Mockito.verify(mockLessonTypeDao, times(1)).save(updatedLessonType);
    }

    @Test
    void updateShouldRaiseExceptionIfIdEqualZero() {
        LessonType updatedLessonType = new LessonType(0, "EXAM");
        assertThrows(InvalidEntityException.class, () -> lessonTypeService.update(updatedLessonType));
    }

}
