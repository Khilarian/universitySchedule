package com.rumakin.universityschedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.*;

@Configuration
@ComponentScan
@PropertySource("classpath::config.properties")
public class ApplicationContextConfiguration {

    @Autowired
    Environment environment;
    
    private final String URL = "db.host";
    private final String USER = "db.login";
    private final String PASSWORD = "db.password";
    private final String DRIVER = "db.driver";
    
    @Bean
    @Scope("singleton")
    public DriverManagerDataSource getDataSource() throws ClassNotFoundException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty(DRIVER));
        dataSource.setUrl(environment.getProperty(URL));
        dataSource.setUsername(environment.getProperty(USER));
        dataSource.setPassword(environment.getProperty(PASSWORD));
        return dataSource;
    }
    
    @Bean
    @Scope("singleton")
    public JdbcTemplate getJdbcTemplate() throws ClassNotFoundException {
        return new JdbcTemplate(getDataSource());
    }

}
