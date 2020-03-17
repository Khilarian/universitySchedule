package com.rumakin.universityschedule;

import com.rumakin.universityschedule.util.DataSourceProvider;
import com.rumakin.universityschedule.util.JdbcTemplateProvider;
import com.rumakin.universityschedule.util.PropertiesLoader;

public class AppRunner {

    public static void main(String[] args) throws ClassNotFoundException {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        DataSourceProvider dataSourceProvider = new DataSourceProvider(propertiesLoader);
        JdbcTemplateProvider jdbcTemplateProvider = new JdbcTemplateProvider(dataSourceProvider.getDataSource());
    }

}
