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
        int id = offices.get(i).getNumber();
        int buildingId = offices.get(i).getBuilding().getId();
        Object openedFrom = offices.get(i).getOpenedFrom();
        Object openedTill = offices.get(i).getOpnedTill();
        ps.setInt(1, id);
        ps.setInt(2, buildingId);
        ps.setObject(3, openedFrom);
        ps.setObject(4, openedTill);
    }

    @Override
    public int getBatchSize() {
        return offices.size();
    }
}
