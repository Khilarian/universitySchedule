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

    @Autowired
    public GroupDao(JdbcTemplate jdbcTemplate, AuditoriumDao auditoriumDao) {
        super(jdbcTemplate);
        this.auditoriumDao = auditoriumDao;
    }

    public List<Auditorium> findAuditoriumOnDate(Group group, LocalDate date) {
        String sql = "SELECT l.auditorium_id FROM " + TABLE + " " + ALIAS
                + " INNER JOIN lesson_group lg ON " + addAlias(ALIAS, ID) + "=" + addAlias("lg", ID)
                + " INNER JOIN lesson l ON lg.lesson_id=l.lesson_id WHERE " + addAlias(ALIAS, ID) + "=? AND l.date=?;";
        Object[] input = { group.getId(), java.sql.Date.valueOf(date) };
        List<Integer> auditoriumsId = this.jdbcTemplate.queryForList(sql, input, Integer.class);
        List<Auditorium> auditoriums = new ArrayList<>();
        for (Integer id : auditoriumsId) {
            Auditorium auditorium = auditoriumDao.find(id);
            auditoriums.add(auditorium);
        }
        return auditoriums;
    }

    @Override
    String getTableName() {
        return TABLE;
    }

    @Override
    String getTableAlias() {
        return ALIAS;
    }

    @Override
    String getEntityIdName() {
        return ID;
    }

    @Override
    List<String> getFieldsNames() {
        return Arrays.asList(NAME);
    }

    @Override
    public RowMapper<Group> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Group(rs.getInt(ID), rs.getString(NAME));
    }

    @Override
    Object[] getFieldValues(Group group) {
        return new Object[] { group.getName() };
    }

}
