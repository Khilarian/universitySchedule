package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

import com.rumakin.universityschedule.models.*;

@Repository
public class AuditoriumDao extends Dao<Auditorium> {

    private static final String TABLE = "auditorium";
    private static final String ALIAS = "a";
    private static final String ID = "auditorium_id";
    private static final String NUMBER = "number_id";
    private static final String CAPACITY = "capacity";
    private static final String BUILDING_ID = "building_id";

    private final BuildingDao buildingDao;

    @Autowired
    public AuditoriumDao(JdbcTemplate jdbcTemplate, BuildingDao buildingDao) {
        super(jdbcTemplate);
        this.buildingDao = buildingDao;
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
        return Arrays.asList(NUMBER, CAPACITY, BUILDING_ID);
    }

    @Override
    public RowMapper<Auditorium> mapRow() {
        return (ResultSet rs, int rowNumber) -> new Auditorium(rs.getInt(ID), rs.getInt(NUMBER),
                rs.getInt(CAPACITY), buildingDao.find(rs.getInt(BUILDING_ID)));
    }

    @Override
    Object[] getFieldValues(Auditorium auditorium) {
        return new Object[] { auditorium.getNumber(), auditorium.getCapacity(), auditorium.getBuilding().getId() };
    }

}
