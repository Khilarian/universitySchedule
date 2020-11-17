package com.rumakin.universityschedule.dao.custom;

import java.time.LocalDate;
import java.util.*;

public interface CustomizedLessonDao {

    boolean isAuditoriumFree(int auditoriumId, int lessonId, LocalDate date, int timeSlotId);
    Set<Integer> getBusyGroupsId(int lessonId, LocalDate date, int timeSlotId);
}
