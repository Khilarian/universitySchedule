package com.rumakin.universityschedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dao.LessonTypeDao;
import com.rumakin.universityschedule.enums.LessonType;

public class LessonTypeService {
    
private final LessonTypeDao lessonTypeDao;
    
    @Autowired
    public LessonTypeService(LessonTypeDao lessonTypeDao) {
        this.lessonTypeDao = lessonTypeDao;
    }
    
    public void add(LessonType lessonType) {
        lessonTypeDao.add(lessonType);
    }
    
    public void addAll(List<LessonType> lessonTypes) {
        lessonTypeDao.addAll(lessonTypes);
    }
    
    public LessonType find(String name) {
        return lessonTypeDao.find(name);
    }
    
    public List<LessonType> findAll(){
        return lessonTypeDao.findAll();
    }
    
    public void remove(int id) {
        lessonTypeDao.remove(id);
    }
}
