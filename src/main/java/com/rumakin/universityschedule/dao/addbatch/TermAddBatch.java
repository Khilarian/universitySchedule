package com.rumakin.universityschedule.dao.addbatch;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.rumakin.universityschedule.models.Term;

public class TermAddBatch implements BatchPreparedStatementSetter {

    private final List<Term> terms;

    public TermAddBatch(final List<Term> data) {
        this.terms = data;
    }

    public final void setValues(final PreparedStatement ps, final int i) throws SQLException {
        ps.setString(1, terms.get(i).name());
    }

    @Override
    public int getBatchSize() {
        return terms.size();
    }
}