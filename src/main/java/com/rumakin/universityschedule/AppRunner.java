package com.rumakin.universityschedule;

import org.springframework.jdbc.core.JdbcTemplate;

import com.rumakin.universityschedule.util.*;

public class AppRunner {

    public static void main(String[] args) throws ClassNotFoundException {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        DataSourceProvider dataSourceProvider = new DataSourceProvider(propertiesLoader);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceProvider.getDataSource());
    }

}
