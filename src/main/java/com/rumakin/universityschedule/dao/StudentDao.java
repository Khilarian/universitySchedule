package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.models.*;

public class StudentDao extends Dao<Student> {

    private static final String TABLE = "student";
    private static final String ALIAS = "s";
    private static final String ID = "person_id";
    private static final String GROUP_ID = "group_id";

    private final PersonDao personDao;
    private final GroupDao groupDao;

    @Autowired
    public StudentDao(JdbcTemplate jdbcTemplate, PersonDao personDao, GroupDao groupDao) {
        super(jdbcTemplate);
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
        Object[] input = { studentId, groupId };
        String sql = prepareSqlAdd(ID);
        this.jdbcTemplate.update(sql, input);
        student.setId(studentId);
        return student;
    }

    @Override
    public Student find(int id) {
        String sql = prepareSqlFind(ID);
        return this.jdbcTemplate.queryForObject(sql, new Object[] { id }, mapRow());
    }

    @Override
    public List<Student> findAll() {
        String sql = prepareSqlFindAll();
        return this.jdbcTemplate.query(sql, mapRow());
    }

    @Override
    public RowMapper<Student> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Student(rs.getInt(ID), rs.getString(2), rs.getString(3),
                groupDao.find(rs.getInt(GROUP_ID)));
    }

    @Override
    public void remove(int id) {
        personDao.remove(id);
    }

    @Override
    protected String getTableAlias() {
        return ALIAS;
    }

    @Override
    protected String getTableName() {
        return TABLE;
    }

    @Override
    protected String getEntityIdName() {
        return ID;
    }

    @Override
    protected List<String> getFieldsNames() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Object[] getFieldValues(Student entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getModelClassName() {
        return Student.class.getSimpleName();
    }

}
