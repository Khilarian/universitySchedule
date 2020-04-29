package com.rumakin.universityschedule.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.*;
import org.springframework.jndi.JndiTemplate;

@Configuration
@ComponentScan("com.rumakin.universityschedule")
@PropertySource("classpath:persistence-jndi.properties")
public class DatabaseConfig {

//    @Value("${db.driver}")
//    private String driver;
//    @Value("${db.host}")
//    private String url;
//    @Value("${db.login}")
//    private String login;
//    @Value("${db.password}")
//    private String password;

    private Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    
    @Autowired
    private Environment env;

//    @Bean
//    @Scope("singleton")
//    public DriverManagerDataSource getDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        logger.info("getDataSource() connect to database: driver {}, url {}, username {}, password {}", driver, url,
//                login, password);
//        dataSource.setDriverClassName(driver);
//        dataSource.setUrl(url);
//        dataSource.setUsername(login);
//        dataSource.setPassword(password);
//        return dataSource;
//    }
    
    @Bean
    @Scope("singleton")
    public DataSource dataSource() throws NamingException {
        return (DataSource) new JndiTemplate().lookup(env.getProperty("jdbc.url"));
    }

    @Bean
    @Scope("singleton")
    public JdbcTemplate getJdbcTemplate() throws NamingException {
        //return new JdbcTemplate(getDataSource());
        return new JdbcTemplate(dataSource());
    }

}
