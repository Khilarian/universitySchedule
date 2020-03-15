package com.rumakin.universityschedule.dao.addbatch;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.rumakin.universityschedule.models.AcademicDegree;

public class AcademicDegreeAddBatch implements BatchPreparedStatementSetter {

    private final List<AcademicDegree> degrees;

    public AcademicDegreeAddBatch(final List<AcademicDegree> data) {
        this.degrees = data;
    }

    public final void setValues(final PreparedStatement ps, final int i) throws SQLException {
        String degreeName = degrees.get(i).name();
        ps.setString(1, degreeName);
    }

    @Override
    public int getBatchSize() {
        return degrees.size();
    }
}
