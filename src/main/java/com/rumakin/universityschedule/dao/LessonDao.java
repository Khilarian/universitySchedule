package com.rumakin.universityschedule.dao;

import java.time.LocalDate;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.models.*;

@Repository
public class LessonDao extends Dao<Lesson> {

    private static final String TABLE = "lesson";
    private static final String ALIAS = "l";
    private static final String ID = "lesson_id";
    private static final String SUBJECT = "subject_id";
    private static final String TYPE = "lesson_type_name";
    private static final String AUDITORIUM_ID = "auditorium_id";
    private static final String DATE = "date";
    private static final String TIME_SLOT = "time_slot";

    private static final String LESSON_TEACHER_TABLE = "lesson_teacher";
    private static final String LESSON_GROUP_TABLE = "lesson_group";
    private static final String TEACHER_ID = "teacher_id";
    private static final String GROUP_ID = "group_id";

    @Autowired
    public LessonDao() {
    }

//    public List<Lesson> findExamsForGroup(int groupId) {
//        logger.debug("findExamsForGroup({})", groupId);
    String sql = "SELECT " + addAlias(ALIAS, ID) + " FROM " + TABLE + " " + ALIAS + " INNER JOIN lesson_group lg ON "
            + addAlias(ALIAS, ID) + "=" + addAlias("lg", ID) + " INNER JOIN lessonType lt ON " + addAlias(ALIAS, TYPE)
            + "=" + addAlias("lt", TYPE) + " WHERE lg.group_id=? AND lt.lesson_type_name=EXAM;";
//        Object[] args = { groupId };
//        List<Integer> examsId = jdbcTemplate.queryForList(sql, args, Integer.class);
//        List<Lesson> exams = new ArrayList<>();
//        for (Integer id : examsId) {
//            Lesson exam = find(id);
//            exams.add(exam);
//        }
//        logger.trace("found {}", exams.size());
//        return exams;
//    }

    public List<Lesson> findLessonsGroupDate(int groupId, LocalDate date) {
        logger.debug("findExamsForGroup({})", groupId);
        String sql = "SELECT " + addAlias(ALIAS, ID) + " FROM " + TABLE + " " + ALIAS
                + " INNER JOIN lesson_group lg ON " + addAlias(ALIAS, ID) + "=" + addAlias("lg", ID)
                + " WHERE lg.group_id=" + groupId + " AND l.date=" + date + ";";
        List<Lesson> lessons = (List<Lesson>) entityManager.createQuery(sql, Lesson.class);
        return lessons;
    }

    public List<Lesson> findScheduleGroupMonth(int groupId, int monthId) {
        return null;
    }

    public List<Lesson> findLessonsTeacherDate(int teacherId, LocalDate date) {
        return null;
    }

    public List<Lesson> findScheduleTeacherMonth(int teacherId, int monthId) {
        return null;
    }

    @Override
    protected String getModelClassName() {
        return Lesson.class.getSimpleName();
    }

    @Override
    protected Class<Lesson> getEntityClass() {
        return Lesson.class;
    }

}
