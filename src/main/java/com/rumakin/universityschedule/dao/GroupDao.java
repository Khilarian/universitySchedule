package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.time.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.models.*;

public class GroupDao extends Dao<Group> {

    private static final String TABLE = "group";
    private static final String ALIAS = "g";
    private static final String ID = "group_id";
    private static final String NAME = "group_name";

    private final AuditoriumDao auditoriumDao;
    private final LessonDao lessonDao;

    @Autowired
    public GroupDao(JdbcTemplate jdbcTemplate, AuditoriumDao auditoriumDao, LessonDao lessonDao) {
        super(jdbcTemplate);
        this.auditoriumDao = auditoriumDao;
        this.lessonDao = lessonDao;
    }

    public List<Auditorium> findAuditoriumOnDate(int groupId, LocalDate date) {
        logger.debug("findAuditoriumOnDate() for groupId {} and date {}", groupId, date);
        String sql = "SELECT l.auditorium_id FROM " + TABLE + " " + ALIAS + " INNER JOIN lesson_group lg ON "
                + addAlias(ALIAS, ID) + "=" + addAlias("lg", ID)
                + " INNER JOIN lesson l ON lg.lesson_id=l.lesson_id WHERE " + addAlias(ALIAS, ID) + "=? AND l.date=?;";
        Object[] input = { groupId, java.sql.Date.valueOf(date) };
        //do we need to check that this groupId was created in data base?
        List<Integer> auditoriumsId = jdbcTemplate.queryForList(sql, input, Integer.class);
        List<Auditorium> auditoriums = new ArrayList<>();
        if (!auditoriumsId.isEmpty()) {
            for (Integer id : auditoriumsId) {
                Auditorium auditorium = auditoriumDao.find(id);
                auditoriums.add(auditorium);
            }
        }
        logger.trace("found {} exams", auditoriums.size());
        return auditoriums;
    }

    public List<Lesson> findExamsForGroup(int groupId) {
        logger.debug("findExamsForGroup({})",groupId);
        String sql = "SELECT l.lesson_id FROM " + TABLE + " " + ALIAS + " INNER JOIN lesson_group lg ON "
                + addAlias("lg", ID) + "=" + addAlias(ALIAS, ID) + " INNER JOIN lesson l ON "
                + addAlias("l", "lesson_id") + "=" + addAlias("lg", "lesson_id")
                + " INNER JOIN lessonType lt ON l.lesson_type_id=lt.lesson_type_id WHERE " + ID
                + "=? AND lt.lesson_type_name=EXAM;";
        Object[] args = { groupId };
        List<Integer> examsId = jdbcTemplate.queryForList(sql, args, Integer.class);
        List<Lesson> exams = new ArrayList<>();
        for (Integer id : examsId) {
            Lesson exam = lessonDao.find(id);
            exams.add(exam);
        }
        logger.trace("found {}", exams.size());
        return exams;
    }

    @Override
    protected String getTableName() {
        return TABLE;
    }

    @Override
    protected String getTableAlias() {
        return ALIAS;
    }

    @Override
    protected String getEntityIdName() {
        return ID;
    }

    @Override
    protected List<String> getFieldsNames() {
        return Arrays.asList(NAME);
    }

    @Override
    public RowMapper<Group> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Group(rs.getInt(ID), rs.getString(NAME));
    }

    @Override
    protected Object[] getFieldValues(Group group) {
        return new Object[] { group.getName() };
    }

    @Override
    protected String getModelClassName() {
        return Group.class.getSimpleName();
    }

}
