package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.time.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.models.*;

public class GroupDao implements Dao<Group> {

    private static final String TABLE = "group";
    private static final String ID = "group_id";
    private static final String NAME = "group_name";

    private static final String ADD = "INSERT INTO " + TABLE + " (" + NAME + ") values (?) RETURNING " + ID + ";";
    private static final String FIND_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + ID + "=?;";
    private static final String FIND_ALL = "SELECT * FROM " + TABLE + ";";
    private static final String REMOVE_BY_ID = "DELETE FROM " + TABLE + " WHERE " + ID + " =?;";

    private final JdbcTemplate jdbcTemplate;
    private final AuditoriumDao auditoriumDao;

    @Autowired
    public GroupDao(JdbcTemplate jdbcTemplate, AuditoriumDao auditoriumDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.auditoriumDao = auditoriumDao;
    }

    public List<Auditorium> findAuditoriumOnDate(int groupId, LocalDate date) {
        String sql = "SELECT l.auditorium_id FROM " + TABLE
                + "g INNER JOIN lesson_group lg ON g.group_id=lg.group_id INNER JOIN lesson l ON lg.lesson_id=l.lesson_id WHERE g.group_id=? AND l.date=?;";
        Object[] input = { groupId, java.sql.Date.valueOf(date) };
        List<Integer> auditoriumsId = this.jdbcTemplate.queryForList(sql, input, Integer.class);
        List<Auditorium> auditoriums = new ArrayList<>();
        for (Integer id : auditoriumsId) {
            Auditorium auditorium = auditoriumDao.find(id);
            auditoriums.add(auditorium);
        }
        return auditoriums;
    }

    @Override
    public List<Group> addAll(List<Group> groups) {
        for (Group group : groups) {
            add(group);
        }
        return groups;
    }

    @Override
    public Group add(Group group) {
        String groupName = group.getName();
        int groupId = this.jdbcTemplate.update(ADD, groupName, Integer.class);
        group.setId(groupId);
        return group;
    }

    @SuppressWarnings("hiding")
    @Override
    public <Integer> Group find(Integer id) {
        return this.jdbcTemplate.queryForObject(FIND_BY_ID, new Object[] { id }, mapRow());
    }

    @Override
    public List<Group> findAll() {
        return this.jdbcTemplate.query(FIND_ALL, mapRow());
    }

    @Override
    public RowMapper<Group> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Group(rs.getInt(ID), rs.getString(NAME));
    }

    @SuppressWarnings("hiding")
    @Override
    public <Integer> void remove(Integer id) {
        this.jdbcTemplate.update(REMOVE_BY_ID, id);
    }

    @Override
    public void setParameters(PreparedStatement ps, Group group) throws SQLException {
        String groupName = group.getName();
        ps.setString(1, groupName);
    }

    @Override
    public String getFieldsList(String alias) {
        List<String> fields = Arrays.asList(ID, NAME);
        return formatFieldsList(alias, fields);
    }

}
