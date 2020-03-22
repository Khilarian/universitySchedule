package com.rumakin.universityschedule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.*;

@Configuration
@ComponentScan
@PropertySource("classpath:config.properties")
public class ApplicationContextConfiguration {

    @Value("${db.driver}")
    private String driver;
    @Value("${db.host}")
    private String url;
    @Value("${db.login}")
    private String login;
    @Value("${db.password}")
    private String password;

    @Bean
    @Scope("singleton")
    public DriverManagerDataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(login);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    @Scope("singleton")
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

}
