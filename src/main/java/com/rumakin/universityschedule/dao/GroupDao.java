package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.time.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.rumakin.universityschedule.models.*;

public class GroupDao extends Dao<Group> {

    private static final String TABLE = "group";
    private static final String ALIAS = "g";
    private static final String ID = "group_id";
    private static final String NAME = "group_name";

    private final AuditoriumDao auditoriumDao;
    private final SubjectDao subjectDao;

    @Autowired
    public GroupDao(JdbcTemplate jdbcTemplate, AuditoriumDao auditoriumDao, SubjectDao subjectDao) {
        super(jdbcTemplate);
        this.auditoriumDao = auditoriumDao;
        this.subjectDao = subjectDao;
    }

    public List<Auditorium> findAuditoriumOnDate(int groupId, LocalDate date) {
        String sql = "SELECT l.auditorium_id FROM " + TABLE + " " + ALIAS
                + " INNER JOIN lesson_group lg ON " + addAlias(ALIAS, ID) + "=" + addAlias("lg", ID)
                + " INNER JOIN lesson l ON lg.lesson_id=l.lesson_id WHERE " + addAlias(ALIAS, ID) + "=? AND l.date=?;";
        Object[] input = { groupId, java.sql.Date.valueOf(date) };
        List<Integer> auditoriumsId = this.jdbcTemplate.queryForList(sql, input, Integer.class);
        List<Auditorium> auditoriums = new ArrayList<>();
        for (Integer id : auditoriumsId) {
            Auditorium auditorium = auditoriumDao.find(id);
            auditoriums.add(auditorium);
        }
        return auditoriums;
    }

    public List<Map<Subject, LocalDate>> findExamForGroup(int groupId) {
        String sql = "SELECT l.subject_id,l.date FROM " + TABLE + " " + ALIAS + " INNER JOIN lesson l ON "
                + addAlias("l", ID) + "=" + addAlias(ALIAS, ID)
                + " INNER JOIN lessonType lt ON l.lesson_type_id=lt.lesson_type_id WHERE " + ID
                + "=? AND lt.lesson_type_name=EXAM;";
        Object[] args = { groupId };
        SqlRowSet rowSet = this.jdbcTemplate.queryForRowSet(sql, args);
        List<Map<Subject, LocalDate>> exams = new ArrayList<>();
        while (rowSet.next()) {
            int subjectId = rowSet.getInt("subject_id");
            Subject subject = subjectDao.find(subjectId);
            LocalDate date = rowSet.getDate("date").toLocalDate();
            Map<Subject, LocalDate> exam = new HashMap<>();
            exam.put(subject, date);
            exams.add(exam);
        }
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
