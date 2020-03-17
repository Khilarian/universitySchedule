package com.rumakin.universityschedule.dao.addbatch;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.rumakin.universityschedule.models.Room;

public class RoomAddBatch implements BatchPreparedStatementSetter {

    private final List<Room> rooms;

    public RoomAddBatch(final List<Room> data) {
        this.rooms = data;
    }

    public final void setValues(final PreparedStatement ps, final int i) throws SQLException {
        int number = rooms.get(i).getNumber();
        int buildingId = rooms.get(i).getBuilding().getId();
        ps.setInt(1, number);
        ps.setInt(2, buildingId);
    }

    @Override
    public int getBatchSize() {
        return rooms.size();
    }
}
