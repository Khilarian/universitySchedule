package com.rumakin.universityschedule.dao.addbatch;

import java.sql.*;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.rumakin.universityschedule.models.Speciality;

public class SpecialityAddBatch implements BatchPreparedStatementSetter {

    private final List<Speciality> specialities;

    public SpecialityAddBatch(final List<Speciality> data) {
        this.specialities = data;
    }

    public final void setValues(final PreparedStatement ps, final int i) throws SQLException {
        int id = specialities.get(i).getId();
        String name = specialities.get(i).getName();
        String degree = specialities.get(i).getAcademicDegree().name();
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, degree);
    }

    @Override
    public int getBatchSize() {
        return specialities.size();
    }
}
