package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

public class BatchComposer<T> implements BatchPreparedStatementSetter {

    private final List<T> data;
    private StatementFiller<T> batch;

    public BatchComposer(final List<T> data, StatementFiller<T> batch) {
        this.data = data;
        this.batch = batch;
    }

    public final void setValues(final PreparedStatement ps, final int i) throws SQLException {
        T t = data.get(i);
        batch.setParameters(ps, t);
    }

    @Override
    public int getBatchSize() {
        return data.size();
    }
}
