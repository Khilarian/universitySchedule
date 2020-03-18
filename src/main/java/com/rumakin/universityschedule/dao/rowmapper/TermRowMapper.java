package com.rumakin.universityschedule.dao.rowmapper;

import java.sql.*;

import org.springframework.jdbc.core.RowMapper;

import com.rumakin.universityschedule.enums.Term;

public class TermRowMapper implements RowMapper<Term> {

    private static final String NAME = "term_name";

    public Term mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Term.valueOf(rs.getString(NAME));
    }
}
