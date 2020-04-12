package com.rumakin.universityschedule.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rumakin.universityschedule.dao.LessonDao;
import com.rumakin.universityschedule.models.Group;
import com.rumakin.universityschedule.models.Lesson;

@Service
public class LessonService {

    private LessonDao lessonDao;
    private Logger logger = LoggerFactory.getLogger(LessonService.class);

    @Autowired
    public LessonService(LessonDao lessonDao) {
        logger.info("Construct with {}", lessonDao);
        this.lessonDao = lessonDao;
    }

    public List<Lesson> findExamsForGroup(Group group) {
        logger.debug("findExamsForGroup() for group {}", group);
        int groupId = group.getId();
        List<Lesson> exams = lessonDao.findExamsForGroup(groupId);
        logger.info("found {} exams", exams.size());
        return exams;
    }

}
