package com.rumakin.universityschedule;

import org.springframework.jdbc.core.JdbcTemplate;

import com.rumakin.universityschedule.util.*;

public class AppRunner {

    public static void main(String[] args) throws ClassNotFoundException {
        PropertiesLoader propertiesLoader = new PropertiesLoader(PropertiesConfiguration.loadProperties());
        DataSourceConfiguration dataSourceConfiguration = new DataSourceConfiguration(propertiesLoader);
        dataSourceConfiguration.configureDataSource();
        DataSourceProvider dataSourceProvider = new DataSourceProvider(dataSourceConfiguration.getDataSource());
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceProvider.getDataSource());
    }

}
