package com.rumakin.universityschedule.dao.addbatch;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.rumakin.universityschedule.models.Faculty;

public class FacultyAddBatch implements BatchPreparedStatementSetter {

    private final List<Faculty> faculties;

    public FacultyAddBatch(final List<Faculty> data) {
        this.faculties = data;
    }

    public final void setValues(final PreparedStatement ps, final int i) throws SQLException {
        ps.setString(1, faculties.get(i).getName());
        ps.setInt(2, faculties.get(i).getOffice().getId());
    }

    @Override
    public int getBatchSize() {
        return faculties.size();
    }
}
