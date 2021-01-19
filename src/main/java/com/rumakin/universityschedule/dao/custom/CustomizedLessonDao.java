package com.rumakin.universityschedule.dao.custom;

import java.time.LocalDate;
import java.util.*;

import com.rumakin.universityschedule.dto.LessonDto;

public interface CustomizedLessonDao {

    boolean isAuditoriumFree(int auditoriumId, Integer lessonId, LocalDate date, int timeSlotId);

    Set<Integer> getBusyGroupsId(Integer lessonId, LocalDate date, int timeSlotId);

    Set<Integer> getBusyTeachersId(Integer lessonId, LocalDate date, int timeSlotId);

    List<LessonDto> getMonthScheduleForTeacher(Integer teacherId, LocalDate startDate, LocalDate endDate);

    List<LessonDto> getMonthScheduleForGroup(Integer groupId, LocalDate startDate, LocalDate endDate);

    List<LessonDto> getDayScheduleForTeacher(Integer teacherId, LocalDate date);

    List<LessonDto> getDayScheduleForGroup(Integer groupId, LocalDate date);

}
