package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.models.*;

public class StudentDao implements Dao<Student> {

    private static final String TABLE = "student";
    private static final String ID = "person_id";
    private static final String GROUP_ID = "group_id";

    private static final String ADD = "INSERT INTO " + TABLE + " (" + ID + "," + GROUP_ID + ") values (?,?);";
    private static final String FIND_BY_ID = "SELECT p.person_id,p.first_name,p.last_name,g.group_id FROM " + TABLE +
            "s INNER JOIN person p ON s." + ID + "=p." + ID + " INNER JOIN group g ON s." + GROUP_ID + "=g." + GROUP_ID
            + " WHERE s." + ID + "=?;";
    private static final String FIND_ALL = "SELECT p.person_id,p.first_name,p.last_name,g.group_id FROM " + TABLE +
            "s INNER JOIN person p ON s." + ID + "=p." + ID + " INNER JOIN group g ON s." + GROUP_ID + "=g." + GROUP_ID;

    private final JdbcTemplate jdbcTemplate;
    private final PersonDao personDao;
    private final GroupDao groupDao;

    @Autowired
    public StudentDao(JdbcTemplate jdbcTemplate, PersonDao personDao, GroupDao groupDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.personDao = personDao;
        this.groupDao = groupDao;
    }

    @Override
    public List<Student> addAll(List<Student> students) {
        for (Student student : students) {
            add(student);
        }
        return students;
    }

    @Override
    public Student add(Student student) {
        int studentId = personDao.add(student).getId();
        int groupId = student.getGroup().getId();
        this.jdbcTemplate.update(ADD, studentId, groupId);
        student.setId(studentId);
        return student;
    }

    @SuppressWarnings("hiding")
    @Override
    public <Integer> Student find(Integer id) {
        return this.jdbcTemplate.queryForObject(FIND_BY_ID, new Object[] { id }, mapRow());
    }

    @Override
    public List<Student> findAll() {
        return this.jdbcTemplate.query(FIND_ALL, mapRow());
    }

    @Override
    public RowMapper<Student> mapRow() {// here struggle(first_name,last_name)-this fields in person, is it ok to use number here?
        return (ResultSet rs, int rowNumber) -> new Student(rs.getInt(ID), rs.getString(2), rs.getString(3),
                groupDao.find(rs.getInt(GROUP_ID)));
    }

    @SuppressWarnings("hiding")
    @Override
    public <Integer> void remove(Integer id) {
        personDao.remove(id);
    }

    @Override
    public void setParameters(PreparedStatement ps, Student student) throws SQLException {
        String studentName = student.getName();
        ps.setString(1, studentName);
    }

    @Override
    public String getFieldsList(String alias) {
        List<String> fields = Arrays.asList(ID, GROUP_ID);
        return formatFieldsList(alias, fields);
    }

}
