package com.rumakin.universityschedule.dao.custom;

import java.time.LocalDate;
import java.util.*;

import com.rumakin.universityschedule.model.Lesson;

public interface CustomizedLessonDao {

    boolean isAuditoriumFree(int auditoriumId, int lessonId, LocalDate date, int timeSlotId);

    Set<Integer> getBusyGroupsId(int lessonId, LocalDate date, int timeSlotId);

    Set<Integer> getBusyTeachersId(int lessonId, LocalDate date, int timeSlotId);

    List<Lesson> findMonthScheduleForTeacher(Integer teacherId, LocalDate startDate, LocalDate endDate);

    List<Lesson> findMonthScheduleForGroup(Integer groupId, LocalDate startDate, LocalDate endDate);

    List<Lesson> findDayScheduleForTeacher(Integer teacherId, LocalDate date);

    List<Lesson> findDayScheduleForGroup(Integer groupId, LocalDate date);

}
