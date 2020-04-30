package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

import com.rumakin.universityschedule.models.*;

@Repository
public class AuditoriumDao extends Dao<Auditorium> {

    private static final String TABLE = "auditorium";
    private static final String ALIAS = "a";
    private static final String ID = "auditorium_id";
    private static final String NUMBER = "auditorium_number";
    private static final String CAPACITY = "capacity";
    private static final String BUILDING_ID = "building_id";

    private final BuildingDao buildingDao;

    @Autowired
    public AuditoriumDao(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory, BuildingDao buildingDao) {
        super(jdbcTemplate, sessionFactory);
        this.buildingDao = buildingDao;
    }

    public List<Auditorium> findAuditoriumOnDate(int groupId, LocalDate date) {
        logger.debug("findAuditoriumOnDate() for groupId {} and date {}", groupId, date);
        String sql = "SELECT " + addAlias(ALIAS, ID) + " FROM " + TABLE + " " + ALIAS + " INNER JOIN lesson l ON "
                + addAlias(ALIAS, ID) + "=" + addAlias("l", ID)
                + " INNER JOIN lesson_group lg ON l.lesson_id=lg.lesson_id WHERE lg.group_id=? AND l.date=?;";
        Object[] input = { groupId, java.sql.Date.valueOf(date) };
        List<Integer> auditoriumsId = jdbcTemplate.queryForList(sql, input, Integer.class);
        List<Auditorium> auditoriums = new ArrayList<>();
        if (!auditoriumsId.isEmpty()) {
            for (Integer id : auditoriumsId) {
                Auditorium auditorium = find(id);
                auditoriums.add(auditorium);
            }
        }
        logger.trace("found {} exams", auditoriums.size());
        return auditoriums;
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
        return Arrays.asList(NUMBER, CAPACITY, BUILDING_ID);
    }

    @Override
    public RowMapper<Auditorium> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Auditorium(rs.getInt(ID), rs.getInt(NUMBER), rs.getInt(CAPACITY),
                buildingDao.find(rs.getInt(BUILDING_ID)));
    }

    @Override
    protected Object[] getFieldValues(Auditorium auditorium) {
        return new Object[] { auditorium.getNumber(), auditorium.getCapacity(), auditorium.getBuilding().getId() };
    }

    @Override
    protected String getModelClassName() {
        return Auditorium.class.getSimpleName();
    }

}
