package com.rumakin.universityschedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dao.LessonTypeDao;
import com.rumakin.universityschedule.models.enums.LessonType;

public class LessonTypeService {
    
private final LessonTypeDao lessonTypeDao;
    
    @Autowired
    public LessonTypeService(LessonTypeDao lessonTypeDao) {
        this.lessonTypeDao = lessonTypeDao;
    }
}
