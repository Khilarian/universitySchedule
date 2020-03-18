package com.rumakin.universityschedule.util;

import java.sql.Driver;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class DataSourceConfiguration {

    private final SimpleDriverDataSource dataSource;
    private final PropertiesLoader propertiesLoader;
    
    public DataSourceConfiguration(PropertiesLoader propertiesLoader) throws ClassNotFoundException {
        this.propertiesLoader = propertiesLoader;
        this.dataSource = new SimpleDriverDataSource();
    }
    
    @SuppressWarnings("unchecked")
    private void configureDataSourse() throws ClassNotFoundException {
        Class<? extends Driver> driver = (Class<? extends Driver>) Class.forName(propertiesLoader.loadDriver());
        dataSource.setDriverClass(driver);
        dataSource.setUrl(propertiesLoader.loadHost());
        dataSource.setUsername(propertiesLoader.loadLogin());
        dataSource.setPassword(propertiesLoader.loadPassword());
    }

    public SimpleDriverDataSource getDataSource() {
        return dataSource;
    }
}
