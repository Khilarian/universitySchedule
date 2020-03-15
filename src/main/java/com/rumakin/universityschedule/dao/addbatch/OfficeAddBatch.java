package com.rumakin.universityschedule.dao.addbatch;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.rumakin.universityschedule.models.Office;

public class OfficeAddBatch implements BatchPreparedStatementSetter {

    private final List<Office> offices;

    public OfficeAddBatch(final List<Office> data) {
        this.offices = data;
    }

    public final void setValues(final PreparedStatement ps, final int i) throws SQLException {
        ps.setInt(1, offices.get(i).getNumber());
        ps.setInt(2, offices.get(i).getBuilding().getId());
        ps.setObject(3, offices.get(i).getOpenedFrom());
        ps.setObject(4, offices.get(i).getOpnedTill());
    }

    @Override
    public int getBatchSize() {
        return offices.size();
    }
}
