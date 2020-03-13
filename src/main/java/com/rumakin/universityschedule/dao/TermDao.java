package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.List;

import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.models.Term;

public class TermDao implements Dao<Term> {
    private static final String TABLE_NAME = "term";
    private static final String ID = "term_id";
    private static final String NAME = "term_name";

    private static final String ADD_TERM = "INSERT INTO " + TABLE_NAME
            + " (" + ID + "," + NAME + ") values (?,?);";
    private static final String FIND_TERM_BY_ID = "SELECT " + NAME + " FROM "
            + TABLE_NAME + " WHERE " + ID + "=?;";
    private static final String FIND_ALL_TERM = "SELECT " + NAME + " FROM "
            + TABLE_NAME + ";";

    public final JdbcTemplate jdbcTemplate;

    public TermDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class TermAddBatch implements BatchPreparedStatementSetter {

        private final List<Term> terms;

        public TermAddBatch(final List<Term> data) {
            this.terms = data;
        }

        public final void setValues(
                final PreparedStatement ps,
                final int i) throws SQLException {
            ps.setInt(1, terms.get(i).getIndex());
            ps.setString(2, terms.get(i).name());
        }

        @Override
        public int getBatchSize() {
            return terms.size();
        }
    }

    @Override
    public void addAll(List<Term> data) {
        this.jdbcTemplate.batchUpdate(ADD_TERM, new TermAddBatch(data));
    }

    @Override
    public void add(Term entity) {
        int termId = entity.getIndex();
        String termName = entity.name();
        this.jdbcTemplate.update(ADD_TERM, termId, termName);
    }

    @Override
    public Term findById(int id) {
        return this.jdbcTemplate.queryForObject(FIND_TERM_BY_ID,
                new Object[] { 1212L },
                new RowMapper<Term>() {
                    public Term mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return Term.valueOf(rs.getString(NAME));
                    }
                });
    }

    @Override
    public List<Term> findAll() {
        return this.jdbcTemplate.query(FIND_ALL_TERM,
                new RowMapper<Term>() {
                    public Term mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return Term.valueOf(rs.getString(NAME));
                    }
                });
    }
}
