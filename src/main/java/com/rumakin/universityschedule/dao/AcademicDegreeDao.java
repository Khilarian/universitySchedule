package com.rumakin.universityschedule.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.rumakin.universityschedule.models.AcademicDegree;

public class AcademicDegreeDao implements Dao<AcademicDegree> {

    private static final String ADD_ACADEMIC_DEGREE = "INSERT INTO academic_degree (degree_id, degree_name) values (?,?);";
    private static final String FIND_ACADEMIC_DEGREE_BY_ID = "SELECT degree_name FROM degree WHERE degree_id=?;";
    private static final String FIND_ALL_ACADEMIC_DEGREE = "SELECT degree_name FROM degree;";

    public final JdbcTemplate jdbcTemplate;

    public AcademicDegreeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addAll(List<AcademicDegree> data) throws DataAccessException, SQLException {
        this.jdbcTemplate.update(prepareAddAllSQLQuery(data), createSqlArray(data));

    }

    @Override
    public void add(AcademicDegree entity) {
        int degreeId = entity.getGradeLevel();
        String degreeName = entity.name();
        this.jdbcTemplate.update(ADD_ACADEMIC_DEGREE, degreeId, degreeName);
    }

    @Override
    public AcademicDegree findById(int id) {
        return this.jdbcTemplate.queryForObject(FIND_ACADEMIC_DEGREE_BY_ID,
                new Object[] { 1212L },
                new RowMapper<AcademicDegree>() {
                    public AcademicDegree mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return AcademicDegree.valueOf(rs.getString("degree_name"));
                    }
                });
    }

    @Override
    public List<AcademicDegree> findAll() {
        return this.jdbcTemplate.query(FIND_ALL_ACADEMIC_DEGREE,
                new RowMapper<AcademicDegree>() {
                    public AcademicDegree mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return AcademicDegree.valueOf(rs.getString("degree_name"));
                    }
                });
    }

    private String prepareAddAllSQLQuery(List<AcademicDegree> data) {
        StringBuilder sqlAddAll = new StringBuilder("INSERT INTO degree (degree_id, degree_name) values ");
        String addBlock = "(?,?),";
        for (int i = 0; i < data.size(); i++) {
            sqlAddAll.append(addBlock);
            if (i == data.size() - 1) {
                sqlAddAll.replace(sqlAddAll.length() - 1, sqlAddAll.length(), ";");
            }
        }
        return sqlAddAll.toString();
    }

    // ??????
    private java.sql.Array createSqlArray(List<AcademicDegree> list) throws SQLException {
        return jdbcTemplate.getDataSource().getConnection().createArrayOf("bigint", list.toArray());
    }

}
