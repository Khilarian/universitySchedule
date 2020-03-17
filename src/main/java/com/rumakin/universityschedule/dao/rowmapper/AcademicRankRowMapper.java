package com.rumakin.universityschedule.dao.rowmapper;

import java.sql.*;

import org.springframework.jdbc.core.RowMapper;

import com.rumakin.universityschedule.models.AcademicRank;

public class AcademicRankRowMapper implements RowMapper<AcademicRank> {

    private static final String NAME = "rank_name";

    public AcademicRank mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AcademicRank.valueOf(rs.getString(NAME));
    }

}
