package com.rumakin.universityschedule.dao.addbatch;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.rumakin.universityschedule.models.LessonType;

public class LessonTypeAddBatch implements BatchPreparedStatementSetter {

    private final List<LessonType> lessonTypes;

    public LessonTypeAddBatch(final List<LessonType> data) {
        this.lessonTypes = data;
    }

    public final void setValues(final PreparedStatement ps, final int i) throws SQLException {
        String name = lessonTypes.get(i).toString();
        ps.setString(1, name);
    }

    @Override
    public int getBatchSize() {
        return lessonTypes.size();
    }
}
