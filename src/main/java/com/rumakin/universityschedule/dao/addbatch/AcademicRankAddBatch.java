package com.rumakin.universityschedule.dao.addbatch;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.rumakin.universityschedule.models.AcademicRank;

public class AcademicRankAddBatch implements BatchPreparedStatementSetter {

    private final List<AcademicRank> ranks;

    public AcademicRankAddBatch(final List<AcademicRank> data) {
        this.ranks = data;
    }

    public final void setValues(final PreparedStatement ps, final int i) throws SQLException {
        String name = ranks.get(i).name();
        ps.setString(1, name);
    }

    @Override
    public int getBatchSize() {
        return ranks.size();
    }
}

