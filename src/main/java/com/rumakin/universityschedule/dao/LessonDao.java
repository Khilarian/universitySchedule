package com.rumakin.universityschedule.dao;

import javax.persistence.EntityManagerFactory;

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

    private static final String ADD_TEACHER = "INSERT INTO " + LESSON_TEACHER_TABLE + " (" + ID + "," + TEACHER_ID
            + ") values (?,?);";
    private static final String ADD_GROUP = "INSERT INTO " + LESSON_GROUP_TABLE + " (" + ID + "," + GROUP_ID
            + ") values (?,?);";
    private static final String FIND_TEACHER_BY_LESSON_ID = "SELECT " + TEACHER_ID + " FROM " + LESSON_TEACHER_TABLE
            + " WHERE " + ID + "=?;";
    private static final String FIND_GROUP_BY_LESSON_ID = "SELECT " + GROUP_ID + " FROM " + LESSON_GROUP_TABLE
            + " WHERE " + ID + "=?;";

    private AuditoriumDao auditoriumDao;
    private SubjectDao subjectDao;
    private TeacherDao teacherDao;
    private GroupDao groupDao;

    @Autowired
    public LessonDao(EntityManagerFactory entityManagerFactory, AuditoriumDao auditoriumDao, SubjectDao subjectDao,
            TeacherDao teacherDao, GroupDao groupDao) {
        super(entityManagerFactory);
        this.auditoriumDao = auditoriumDao;
        this.subjectDao = subjectDao;
        this.teacherDao = teacherDao;
        this.groupDao = groupDao;
    }

//    public List<Lesson> findExamsForGroup(int groupId) {
//        logger.debug("findExamsForGroup({})", groupId);
//        String sql = "SELECT " + addAlias(ALIAS, ID) + " FROM " + TABLE + " " + ALIAS
//                + " INNER JOIN lesson_group lg ON " + addAlias(ALIAS, ID) + "=" + addAlias("lg", ID)
//                + " INNER JOIN lessonType lt ON " + addAlias(ALIAS, TYPE) + "=" + addAlias("lt", TYPE)
//                + " WHERE lg.group_id=? AND lt.lesson_type_name=EXAM;";
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

    @Override
    protected String getModelClassName() {
        return Lesson.class.getSimpleName();
    }

    @Override
    protected Class<Lesson> getEntityClass() {
        return Lesson.class;
    }

}
