package com.rumakin.universityschedule.util;

import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTemplateConfig {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplateConfig(BaseDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource.getDataSource());
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

}
