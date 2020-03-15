package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.dao.addbatch.AcademicRankAddBatch;
import com.rumakin.universityschedule.models.AcademicRank;

public class AcademicRankDao implements Dao<AcademicRank> {
    private static final String TABLE_NAME = "course_rank";
    private static final String NAME = "rank_name";

    private static final String ADD_ACADEMIC_RANK = "INSERT INTO " + TABLE_NAME + " (" + NAME + ") values (?);";
    private static final String FIND_BY_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + "=?;";
    private static final String FIND_ALL_ACADEMIC_RANK = "SELECT * FROM " + TABLE_NAME + ";";

    public final JdbcTemplate jdbcTemplate;

    public AcademicRankDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addAll(List<AcademicRank> data) {
        this.jdbcTemplate.batchUpdate(ADD_ACADEMIC_RANK, new AcademicRankAddBatch(data));
    }

    @Override
    public void add(AcademicRank entity) {
        String rankName = entity.name();
        this.jdbcTemplate.update(ADD_ACADEMIC_RANK, rankName);
    }

    @Override
    public AcademicRank findById(int id) {
        return null;
    }

    public AcademicRank findByName(String name) {
        return this.jdbcTemplate.queryForObject(FIND_BY_NAME,
                new Object[] { name },
                new RowMapper<AcademicRank>() {
                    public AcademicRank mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return AcademicRank.valueOf(rs.getString(NAME));
                    }
                });
    }

    @Override
    public List<AcademicRank> findAll() {
        return this.jdbcTemplate.query(FIND_ALL_ACADEMIC_RANK,
                new RowMapper<AcademicRank>() {
                    public AcademicRank mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return AcademicRank.valueOf(rs.getString(NAME));
                    }
                });
    }

}
