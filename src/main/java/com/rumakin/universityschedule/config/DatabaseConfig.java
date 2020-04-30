package com.rumakin.universityschedule.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jndi.JndiTemplate;

import com.rumakin.universityschedule.utils.HibernateSessionFactory;


@Configuration
public class DatabaseConfig {

    private Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    
    @Autowired
    private Environment environment;

    
    @Bean
    @Scope("singleton")
    public DataSource dataSource() throws NamingException {
        String propertiesPath = environment.getProperty("jdbc.url");
        logger.info("dataSource() with {}", propertiesPath);
        return (DataSource) new JndiTemplate().lookup(propertiesPath);
    }

    @Bean
    @Scope("singleton")
    public JdbcTemplate getJdbcTemplate() throws NamingException {
        return new JdbcTemplate(dataSource());
    }
    
    @Bean
    @Scope("singleton")
    public SessionFactory getSessionFactory() {
        return HibernateSessionFactory.getSessionFactory();
    }

}
