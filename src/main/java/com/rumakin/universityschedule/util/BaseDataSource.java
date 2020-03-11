package com.rumakin.universityschedule.util;

import java.sql.Driver;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class BaseDataSource {

    private SimpleDriverDataSource dataSource;
    private PropertiesLoader propertiesLoader;

    public BaseDataSource(PropertiesLoader propertiesLoader) throws ClassNotFoundException {
        this.propertiesLoader = propertiesLoader;
        configureDataSourse();
    }

    @SuppressWarnings("unchecked")
    private void configureDataSourse() throws ClassNotFoundException {
        dataSource.setDriverClass((Class<? extends Driver>) Class.forName(propertiesLoader.loadDriver()));
        dataSource.setUrl(propertiesLoader.loadHost());
        dataSource.setUsername(propertiesLoader.loadLogin());
        dataSource.setPassword(propertiesLoader.loadPassword());
    }

    public SimpleDriverDataSource getDataSource() {
        return dataSource;
    }
}
