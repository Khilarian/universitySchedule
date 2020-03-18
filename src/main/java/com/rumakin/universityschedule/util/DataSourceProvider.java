package com.rumakin.universityschedule.util;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class DataSourceProvider {

    private final SimpleDriverDataSource dataSource;

    public DataSourceProvider(SimpleDriverDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public SimpleDriverDataSource getDataSource() {
        return dataSource;
    }
}
