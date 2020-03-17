package com.rumakin.universityschedule.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.rumakin.universityschedule.dao.addbatch.SpecialityAddBatch;
import com.rumakin.universityschedule.dao.rowmapper.SpecialityRowMapper;
import com.rumakin.universityschedule.exceptions.DaoException;
import com.rumakin.universityschedule.models.Speciality;

public class SpecialityDao implements Dao<Speciality> {

    private static final String TABLE_NAME = "speciality";
    private static final String ID = "speciality_id";
    private static final String NAME = "speciality_name";
    private static final String DEGREE = "degree_name";

    private static final String ADD = "INSERT INTO " + TABLE_NAME + " (" + NAME + "," + DEGREE
            + ") values (?,?);";
    private static final String FIND_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + "=?;";
    private static final String FIND_BY_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + "=?;";
    private static final String FIND_ALL = "SELECT * FROM " + TABLE_NAME + ";";

    public final JdbcTemplate jdbcTemplate;

    public SpecialityDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addAll(List<Speciality> data) {
        this.jdbcTemplate.batchUpdate(ADD, new SpecialityAddBatch(data));
    }

    @Override
    public void add(Speciality speciality) {
        this.jdbcTemplate.update(ADD, speciality);
    }

    @Override
    public Speciality findById(int id) {
        Speciality speciality = this.jdbcTemplate.queryForObject(FIND_BY_ID, new Object[] { id },
                new SpecialityRowMapper());
        if (speciality == null) {
            throw new DaoException("Speciality with id " + id + " is absent.");
        }
        return speciality;
    }

    public Speciality findByName(String name) {
        Speciality speciality = this.jdbcTemplate.queryForObject(FIND_BY_NAME, new Object[] { name },
                new SpecialityRowMapper());
        if (speciality == null) {
            throw new DaoException("Speciality with name " + name + " is absent.");
        }
        return speciality;
    }

    @Override
    public List<Speciality> findAll() {
        List<Speciality> specialities = this.jdbcTemplate.query(FIND_ALL, new SpecialityRowMapper());
        if (specialities.isEmpty()) {
            throw new DaoException("Speciality table is empty.");
        }
        return specialities;
    }

}
