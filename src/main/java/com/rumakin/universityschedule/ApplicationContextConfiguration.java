package com.rumakin.universityschedule;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Driver;
import java.util.Properties;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
@ComponentScan
public class ApplicationContextConfiguration {

    private static final String PROPERTIES_PATH = "src\\main\\resources\\config.properties";

    @Bean
    @Scope("singleton")
    private Properties getProperties() {
        Properties properties = new Properties();
        try (FileInputStream stream = new FileInputStream(PROPERTIES_PATH)) {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    @Bean
    @Scope("singleton")
    public String loadDriver() {
        return getProperties().getProperty("db.driver");
    }

    @Bean
    @Scope("singleton")
    public String loadLogin() {
        return getProperties().getProperty("db.login");
    }

    @Bean
    @Scope("singleton")
    public String loadPassword() {
        return getProperties().getProperty("db.password");
    }

    @Bean
    @Scope("singleton")
    public String loadHost() {
        return getProperties().getProperty("db.host");
    }

    @Bean
    @Scope("singleton")
    public String loadTables() {
        return getProperties().getProperty("db.tables");
    }

    @Bean
    @Scope("singleton")
    public SimpleDriverDataSource getDataSource() throws ClassNotFoundException {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        Class<? extends Driver> driver = (Class<? extends Driver>) Class.forName(loadDriver());
        dataSource.setDriverClass(driver);
        dataSource.setUrl(loadHost());
        dataSource.setUsername(loadLogin());
        dataSource.setPassword(loadPassword());
        return dataSource;
    }
    
    @Bean
    @Scope("singleton")
    public JdbcTemplate getJdbcTemplate() throws ClassNotFoundException {
        return new JdbcTemplate(getDataSource());
    }

}
