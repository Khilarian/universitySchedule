package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.models.AcademicRank;

public class AcademicRankDao implements Dao<AcademicRank> {
    private static final String TABLE_NAME = "course_rank";
    private static final String ID = "rank_id";
    private static final String NAME = "rank_name";

    private static final String ADD_ACADEMIC_RANK = "INSERT INTO " + TABLE_NAME
            + " (" + ID + "," + NAME + ") values (?,?);";
    private static final String FIND_ACADEMIC_RANK_BY_ID = "SELECT " + NAME + " FROM "
            + TABLE_NAME + " WHERE " + ID + "=?;";
    private static final String FIND_ALL_ACADEMIC_RANK = "SELECT " + NAME + " FROM "
            + TABLE_NAME + ";";

    public final JdbcTemplate jdbcTemplate;

    public AcademicRankDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class AcademicRankAddBatch implements BatchPreparedStatementSetter {

        private final List<AcademicRank> ranks;

        public AcademicRankAddBatch(final List<AcademicRank> data) {
            this.ranks = data;
        }

        public final void setValues(
                final PreparedStatement ps,
                final int i) throws SQLException {
            ps.setInt(1, ranks.get(i).getRankLevel());
            ps.setString(2, ranks.get(i).name());
        }

        @Override
        public int getBatchSize() {
            return ranks.size();
        }
    }

    @Override
    public void addAll(List<AcademicRank> data) {
        this.jdbcTemplate.batchUpdate(ADD_ACADEMIC_RANK, new AcademicRankAddBatch(data));
    }

    @Override
    public void add(AcademicRank entity) {
        int rankId = entity.getRankLevel();
        String rankName = entity.name();
        this.jdbcTemplate.update(ADD_ACADEMIC_RANK, rankId, rankName);
    }

    @Override
    public AcademicRank findById(int id) {
        return this.jdbcTemplate.queryForObject(FIND_ACADEMIC_RANK_BY_ID,
                new Object[] { 1212L },
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
