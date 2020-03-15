package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.List;

import org.springframework.jdbc.core.*;

import com.rumakin.universityschedule.dao.addbatch.TermAddBatch;
import com.rumakin.universityschedule.models.Term;

public class TermDao implements Dao<Term> {
    private static final String TABLE_NAME = "term";
    private static final String NAME = "term_name";

    private static final String ADD_TERM = "INSERT INTO " + TABLE_NAME + " (" + NAME + ") values (?);";
    private static final String FIND_BY_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + "=?;";
    private static final String FIND_ALL_TERM = "SELECT * FROM " + TABLE_NAME + ";";

    public final JdbcTemplate jdbcTemplate;

    public TermDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addAll(List<Term> data) {
        this.jdbcTemplate.batchUpdate(ADD_TERM, new TermAddBatch(data));
    }

    @Override
    public void add(Term entity) {
        String termName = entity.name();
        this.jdbcTemplate.update(ADD_TERM, termName);
    }

    public Term findByName(String name) {
        return this.jdbcTemplate.queryForObject(FIND_BY_NAME, 
                new Object[] { name },
                new RowMapper<Term>() {
                    public Term mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return Term.valueOf(rs.getString(NAME));
                    }
                });
    }

    @Override
    public Term findById(int id) {
        return null;
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
