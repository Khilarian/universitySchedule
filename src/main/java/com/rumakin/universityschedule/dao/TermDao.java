package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.List;

import org.springframework.jdbc.core.*;
import com.rumakin.universityschedule.enums.Term;
import com.rumakin.universityschedule.exceptions.DaoException;

public class TermDao implements Dao<Term>, PreparedStatementBatchSetter<Term> {
    private static final String TABLE_NAME = "term";
    private static final String NAME = "term_name";

    private static final String ADD = "INSERT INTO " + TABLE_NAME + " (" + NAME + ") values (?);";
    private static final String FIND_BY_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + "=?;";
    private static final String FIND_ALL = "SELECT * FROM " + TABLE_NAME + ";";

    public final JdbcTemplate jdbcTemplate;

    public TermDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addAll(List<Term> data) {
        this.jdbcTemplate.batchUpdate(ADD, new BatchComposer<Term>(data, this));
    }

    @Override
    public void add(Term term) {
        String termName = term.name();
        this.jdbcTemplate.update(ADD, termName);
    }

    public Term findByName(String name) {
        Term term = this.jdbcTemplate.queryForObject(FIND_BY_NAME, new Object[] { name },
                mapRow());
        if (term == null) {
            throw new DaoException("Term with name " + name + " is absent.");
        }
        return term;
    }

    @Override
    public Term findById(int id) {
        return null;
    }

    @Override
    public List<Term> findAll() {
        List<Term> terms = this.jdbcTemplate.query(FIND_ALL, mapRow());
        if (terms.isEmpty()) {
            throw new DaoException("AcademicDegree table is empty.");
        }
        return terms;
    }

    @Override
    public void setStatements(PreparedStatement ps, Term term) throws SQLException {
        String termName = term.name();
        ps.setString(1, termName);
    }

    @Override
    public RowMapper<Term> mapRow() {
        return (ResultSet rs, int rowNumver) -> Term.valueOf(rs.getString(NAME));
    }
}
