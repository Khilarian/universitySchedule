package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.models.Auditorium;
import com.rumakin.universityschedule.models.Group;
import com.rumakin.universityschedule.models.Lesson;
import com.rumakin.universityschedule.models.Teacher;
import com.rumakin.universityschedule.models.enums.LessonType;
import com.rumakin.universityschedule.models.enums.TimeSlot;

public class LessonDao implements Dao<Lesson> {

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

    private static final String ADD = "INSERT INTO " + TABLE + " (" + SUBJECT + "," + TYPE + "," + AUDITORIUM_ID + ","
            + DATE + "," + TIME_SLOT + ") values (?,?,?,?,?) RETURNING " + ID + ";";
    private static final String ADD_TEACHER = "INSERT INTO " + LESSON_TEACHER_TABLE + " (" + ID + "," + TEACHER_ID
            + ") values (?,?);";
    private static final String ADD_GROUP = "INSERT INTO " + LESSON_GROUP_TABLE + " (" + ID + "," + GROUP_ID
            + ") values (?,?);";

    private static final String FIND_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + ID + "=?;";
    private static final String FIND_ALL = "SELECT * FROM " + TABLE + ";";
    private static final String REMOVE_BY_ID = "DELETE FROM " + TABLE + " WHERE " + ID + " =?;";

    private JdbcTemplate jdbcTemplate;
    private AuditoriumDao auditoriumDao;
    private SubjectDao subjectDao;

    @Autowired
    public LessonDao(JdbcTemplate jdbcTemplate, AuditoriumDao auditoriumDao, SubjectDao subjectDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.auditoriumDao = auditoriumDao;
        this.subjectDao = subjectDao;
    }

    @Override
    public void addAll(List<Lesson> data) {
        this.jdbcTemplate.batchUpdate(ADD, new BatchComposer<Lesson>(data, this));
    }

    @Override
    public void add(Lesson lesson) {
        int subjectId = lesson.getSubject().getId(); // subject has id already?
        String lessonType = lesson.getLessonType().name();
        int auditoriumId = lesson.getAuditorium().getId(); // same as subject
        Object date = lesson.getDate();
        String timeSlot = lesson.getTimeSlot().name();
        Object[] inputData = { subjectId, lessonType, auditoriumId, date, timeSlot };
        int id = this.jdbcTemplate.queryForObject(ADD, inputData, Integer.class);

        List<Teacher> teachers = lesson.getTeachers();
        this.jdbcTemplate.batchUpdate(ADD_TEACHER, new BatchPreparedStatementSetter() {//must be additional private
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

        List<Group> groups = lesson.getGroups();
        this.jdbcTemplate.batchUpdate(ADD_GROUP, new BatchPreparedStatementSetter() {//must be additional private
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

    @Override
    public <Integer> Lesson find(Integer id) {
        return this.jdbcTemplate.queryForObject(FIND_BY_ID, new Object[] { id }, mapRow());
    }

    @Override
    public List<Lesson> findAll() {
        return this.jdbcTemplate.query(FIND_ALL, mapRow());
    }

    @Override
    public RowMapper<Lesson> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Lesson(rs.getInt(ID), subjectDao.find(rs.getInt(SUBJECT)),LessonType.valueOf(rs.getString(TYPE)),
                auditoriumDao.find(rs.getInt(AUDITORIUM_ID)), rs.getObject(DATE), TimeSlot.valueOf(rs.getString(TIME_SLOT)));
    }

    @Override
    public <Integer> void remove(Integer id) {
        this.jdbcTemplate.update(REMOVE_BY_ID, id);
    }

    @Override
    public void setParameters(PreparedStatement ps, Lesson t) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public String getFieldsList() {
        return ALIAS + "." + ID + "," + ALIAS + "." + SUBJECT + "," + ALIAS + "." + TYPE + "," + ALIAS + "."
                + AUDITORIUM_ID + "," + ALIAS + "." + DATE + "," + ALIAS + "." + TIME_SLOT;
    }

    @Override
    public String getTableName() {
        return TABLE;
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }

}
