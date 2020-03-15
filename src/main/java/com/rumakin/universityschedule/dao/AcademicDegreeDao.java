package com.rumakin.universityschedule.dao;

import java.util.*;

import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.dao.addbatch.AcademicDegreeAddBatch;
import com.rumakin.universityschedule.dao.rowmapper.AcademicDegreeRowMapper;
import com.rumakin.universityschedule.exceptions.DaoException;
import com.rumakin.universityschedule.models.AcademicDegree;

public class AcademicDegreeDao implements Dao<AcademicDegree> {

    private static final String TABLE_NAME = "academic_degree";
    private static final String NAME = "degree_name";

    private static final String ADD_ACADEMIC_DEGREE = "INSERT INTO " + TABLE_NAME + " (" + NAME + ") values (?);";
    private static final String FIND_BY_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + "=?;";
    private static final String FIND_ALL_ACADEMIC_DEGREE = "SELECT * FROM " + TABLE_NAME + ";";

    public final JdbcTemplate jdbcTemplate;

    public AcademicDegreeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addAll(List<AcademicDegree> data) {
        this.jdbcTemplate.batchUpdate(ADD_ACADEMIC_DEGREE, new AcademicDegreeAddBatch(data));
    }

    @Override
    public void add(AcademicDegree entity) {
        String degreeName = entity.name();
        this.jdbcTemplate.update(ADD_ACADEMIC_DEGREE, degreeName);
    }

    public AcademicDegree findByName(String name) {
        AcademicDegree degree = this.jdbcTemplate.queryForObject(FIND_BY_NAME, new Object[] { name },
                new AcademicDegreeRowMapper());
        if (degree == null) {
            throw new DaoException("AcademicDegree with name " + name + " is absent.");
        }
        return degree;
    }

    @Override
    public AcademicDegree findById(int id) {
        return null;
    }

    @Override
    public List<AcademicDegree> findAll() {
        List<AcademicDegree> degrees = this.jdbcTemplate.query(FIND_ALL_ACADEMIC_DEGREE, new AcademicDegreeRowMapper());
        if (degrees.isEmpty()) {
            throw new DaoException("AcademicDegree table is empty.");
        }
        return degrees;
    }

}
