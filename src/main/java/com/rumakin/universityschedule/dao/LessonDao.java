package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.models.*;
import com.rumakin.universityschedule.models.enums.*;

public class LessonDao implements Dao<Lesson> {

    private static final String TABLE = "lesson";
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

    private static final String ADD = "INSERT INTO " + TABLE + " (" + SUBJECT + "," + TYPE + "," + AUDITORIUM_ID + ","
            + DATE + "," + TIME_SLOT + ") values (?,?,?,?,?) RETURNING " + ID + ";";
    private static final String ADD_TEACHER = "INSERT INTO " + LESSON_TEACHER_TABLE + " (" + ID + "," + TEACHER_ID
            + ") values (?,?);";
    private static final String ADD_GROUP = "INSERT INTO " + LESSON_GROUP_TABLE + " (" + ID + "," + GROUP_ID
            + ") values (?,?);";

    private static final String FIND_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + ID + "=?;";
    private static final String FIND_ALL = "SELECT * FROM " + TABLE + ";";
    private static final String REMOVE_BY_ID = "DELETE FROM " + TABLE + " WHERE " + ID + " =?;";

    private static final String FIND_TEACHER_BY_LESSON_ID = "SELECT " + TEACHER_ID + " FROM " + LESSON_TEACHER_TABLE
            + " WHERE " + ID + "=?;";
    private static final String FIND_GROUP_BY_LESSON_ID = "SELECT " + GROUP_ID + " FROM " + LESSON_GROUP_TABLE
            + " WHERE " + ID + "=?;";

    private JdbcTemplate jdbcTemplate;
    private AuditoriumDao auditoriumDao;
    private SubjectDao subjectDao;
    private TeacherDao teacherDao;
    private GroupDao groupDao;

    @Autowired
    public LessonDao(JdbcTemplate jdbcTemplate, AuditoriumDao auditoriumDao, SubjectDao subjectDao,
            TeacherDao teacherDao, GroupDao groupDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.auditoriumDao = auditoriumDao;
        this.subjectDao = subjectDao;
        this.teacherDao = teacherDao;
        this.groupDao = groupDao;
    }

    @Override
    public Lesson add(Lesson lesson) {
        int id = addToDataBase(lesson);
        addToLessonTeacher(id, lesson.getTeachers());
        addToLessonGroup(id, lesson.getGroups());
        lesson.setId(id);
        return lesson;
    }

    @Override
    public List<Lesson> addAll(List<Lesson> lessons) {
        for (Lesson lesson:lessons) {
            add(lesson);
        }
        return lessons;
    }

    @SuppressWarnings("hiding")
    @Override
    public <Integer> Lesson find(Integer lessonId) {
        Lesson lesson = this.jdbcTemplate.queryForObject(FIND_BY_ID, new Object[] { lessonId }, mapRow());
        lesson.setTeachers(findAllTeachers(lesson.getId()));
        lesson.setGroups(findAllGroups(lesson.getId()));
        return lesson;
    }

    @Override
    public List<Lesson> findAll() {
        List<Lesson> lessons = this.jdbcTemplate.query(FIND_ALL, mapRow());
        for (Lesson lesson : lessons) {
            lesson.setTeachers(findAllTeachers(lesson.getId()));
            lesson.setGroups(findAllGroups(lesson.getId()));
        }
        return lessons;
    }

    @Override
    public RowMapper<Lesson> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Lesson(rs.getInt(ID), subjectDao.find(rs.getInt(SUBJECT)),
                LessonType.valueOf(rs.getString(TYPE)),
                auditoriumDao.find(rs.getInt(AUDITORIUM_ID)), ((java.sql.Date) rs.getObject(DATE)).toLocalDate(),
                TimeSlot.valueOf(rs.getString(TIME_SLOT)));
    }

    @SuppressWarnings("hiding")
    @Override
    public <Integer> void remove(Integer id) {
        this.jdbcTemplate.update(REMOVE_BY_ID, id);
    }

    @Override
    public void setParameters(PreparedStatement ps, Lesson t) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public String getFieldsList(String alias) {
        List<String> fields = Arrays.asList(ID, SUBJECT, TYPE, AUDITORIUM_ID, DATE, TIME_SLOT);
        return formatFieldsList(alias, fields);
    }

    private int addToDataBase(Lesson lesson) {
        int subjectId = lesson.getSubject().getId();
        String lessonType = lesson.getLessonType().name();
        int auditoriumId = lesson.getAuditorium().getId();
        Object date = java.sql.Date.valueOf(lesson.getDate());
        String timeSlot = lesson.getTimeSlot().name();
        Object[] inputData = { subjectId, lessonType, auditoriumId, date, timeSlot };
        return this.jdbcTemplate.queryForObject(ADD, inputData, Integer.class);
    }

    private void addToLessonTeacher(int id, List<Teacher> teachers) {
        this.jdbcTemplate.batchUpdate(ADD_TEACHER, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, teachers.get(i).getId());
            }

            @Override
            public int getBatchSize() {
                return teachers.size();
            }
        });
    }

    private void addToLessonGroup(int id, List<Group> groups) {
        this.jdbcTemplate.batchUpdate(ADD_GROUP, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, groups.get(i).getId());
            }

            @Override
            public int getBatchSize() {
                return groups.size();
            }
        });
    }

    private List<Teacher> findAllTeachers(Integer lessonId) {
        List<Teacher> teachers = new ArrayList<>();
        List<Integer> teachersId = this.jdbcTemplate.queryForList(FIND_TEACHER_BY_LESSON_ID, new Object[] { lessonId },
                Integer.class);
        for (int id : teachersId) {
            teachers.add(teacherDao.find(id));
        }
        return teachers;
    }

    private List<Group> findAllGroups(Integer lessonId) {
        List<Group> groups = new ArrayList<>();
        List<Integer> groupsId = this.jdbcTemplate.queryForList(FIND_GROUP_BY_LESSON_ID, new Object[] { lessonId },
                Integer.class);
        for (int id : groupsId) {
            groups.add(groupDao.find(id));
        }
        return groups;
    }

}
