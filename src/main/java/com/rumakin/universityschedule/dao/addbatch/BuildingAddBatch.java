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
        String name = buildings.get(i).getName();
        String address = buildings.get(i).getAddress();
        
        ps.setString(1, name);
        ps.setString(2, address);
    }

    @Override
    public int getBatchSize() {
        return buildings.size();
    }
}
