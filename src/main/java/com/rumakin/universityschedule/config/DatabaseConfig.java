package com.rumakin.universityschedule.config;

import javax.persistence.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
public class DatabaseConfig {

    private Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    @Autowired
    private Environment environment;

    @Bean
    @Scope("singleton")
    public EntityManagerFactory getEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("com.rumakin.universityschedule");
    }
    
    @Bean
    @Scope("singleton")
    public EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }
}
