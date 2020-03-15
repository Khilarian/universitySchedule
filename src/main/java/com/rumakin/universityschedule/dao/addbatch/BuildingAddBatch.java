package com.rumakin.universityschedule.dao.addbatch;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.rumakin.universityschedule.models.Building;

public class BuildingAddBatch implements BatchPreparedStatementSetter {

    private final List<Building> buildings;

    public BuildingAddBatch(final List<Building> data) {
        this.buildings = data;
    }

    public final void setValues(final PreparedStatement ps, final int i) throws SQLException {
        ps.setString(1, buildings.get(i).getName());
        ps.setString(2, buildings.get(i).getAddress());
    }

    @Override
    public int getBatchSize() {
        return buildings.size();
    }
}