package com.rumakin.universityschedule.dao.addbatch;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.rumakin.universityschedule.models.CourseNumber;

public class CourseNumberAddBatch implements BatchPreparedStatementSetter {

    private final List<CourseNumber> courses;

    public CourseNumberAddBatch(final List<CourseNumber> data) {
        this.courses = data;
    }

    public final void setValues(final PreparedStatement ps, final int i) throws SQLException {
        ps.setString(1, courses.get(i).name());
    }

    @Override
    public int getBatchSize() {
        return courses.size();
    }
}
