package com.rumakin.universityschedule.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class JdbcTemplateProvider {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateProvider(SimpleDriverDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

}
