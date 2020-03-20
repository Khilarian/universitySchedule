package com.rumakin.universityschedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.*;

@Configuration
@ComponentScan
@PropertySource("classpath:config.properties")
public class ApplicationContextConfiguration {

    @Autowired
    Environment environment;
    
    private static final String URL = "db.host";
    private static final String USER = "db.login";
    private static final String PASSWORD = "db.password";
    private static final String DRIVER = "db.driver";
    @Value("${db.password}")
    private String password;
    
    
    
    @Bean
    @Scope("singleton")
    public DriverManagerDataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty(DRIVER));
        dataSource.setUrl(environment.getProperty(URL));
        dataSource.setUsername(environment.getProperty(USER));
        dataSource.setPassword(password);
        return dataSource;
    }
    
    @Bean
    @Scope("singleton")
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

}
