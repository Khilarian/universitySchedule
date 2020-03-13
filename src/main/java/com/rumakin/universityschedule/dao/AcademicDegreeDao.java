package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.models.AcademicDegree;

public class AcademicDegreeDao implements Dao<AcademicDegree> {

    private static final String TABLE_NAME = "academic_degree";
    private static final String ID = "degree_id";
    private static final String NAME = "degree_name";

    private static final String ADD_ACADEMIC_DEGREE = "INSERT INTO " + TABLE_NAME
            + " (" + ID + "," + NAME + ") values (?,?);";
    private static final String FIND_ACADEMIC_DEGREE_BY_ID = "SELECT " + NAME + " FROM "
            + TABLE_NAME + " WHERE " + ID + "=?;";
    private static final String FIND_ALL_ACADEMIC_DEGREE = "SELECT " + NAME + " FROM "
            + TABLE_NAME + ";";

    public final JdbcTemplate jdbcTemplate;

    public AcademicDegreeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class AcademicDegreeAddBatch implements BatchPreparedStatementSetter {

        private final List<AcademicDegree> degrees;

        public AcademicDegreeAddBatch(final List<AcademicDegree> data) {
            this.degrees = data;
        }

        public final void setValues(
                final PreparedStatement ps,
                final int i) throws SQLException {
            ps.setInt(1, degrees.get(i).getGradeLevel());
            ps.setString(2, degrees.get(i).name());
        }

        @Override
        public int getBatchSize() {
            return degrees.size();
        }
    }

    @Override
    public void addAll(List<AcademicDegree> data) {
        this.jdbcTemplate.batchUpdate(ADD_ACADEMIC_DEGREE, new AcademicDegreeAddBatch(data));
    }

    @Override
    public void add(AcademicDegree entity) {
        int degreeId = entity.getGradeLevel();
        String degreeName = entity.name();
        this.jdbcTemplate.update(ADD_ACADEMIC_DEGREE, degreeId, degreeName);
    }

    @Override
    public AcademicDegree findById(int id) {
        return this.jdbcTemplate.queryForObject(FIND_ACADEMIC_DEGREE_BY_ID,
                new Object[] { 1212L },
                new RowMapper<AcademicDegree>() {
                    public AcademicDegree mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return AcademicDegree.valueOf(rs.getString("degree_name"));
                    }
                });
    }

    @Override
    public List<AcademicDegree> findAll() {
        return this.jdbcTemplate.query(FIND_ALL_ACADEMIC_DEGREE,
                new RowMapper<AcademicDegree>() {
                    public AcademicDegree mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return AcademicDegree.valueOf(rs.getString("degree_name"));
                    }
                });
    }

}
