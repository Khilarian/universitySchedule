package com.rumakin.universityschedule.dao.custom;

import java.time.LocalDate;
import java.util.*;

import com.rumakin.universityschedule.dto.LessonDto;

public interface CustomizedLessonDao {

    boolean isAuditoriumFree(int auditoriumId, int lessonId, LocalDate date, int timeSlotId);

    Set<Integer> getBusyGroupsId(int lessonId, LocalDate date, int timeSlotId);

    Set<Integer> getBusyTeachersId(int lessonId, LocalDate date, int timeSlotId);

    List<LessonDto> findMonthScheduleForTeacher(Integer teacherId, LocalDate startDate, LocalDate endDate);

    List<LessonDto> findMonthScheduleForGroup(Integer groupId, LocalDate startDate, LocalDate endDate);

    List<LessonDto> findDayScheduleForTeacher(Integer teacherId, LocalDate date);

    List<LessonDto> findDayScheduleForGroup(Integer groupId, LocalDate date);

}
