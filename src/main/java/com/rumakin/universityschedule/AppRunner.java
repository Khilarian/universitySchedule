package com.rumakin.universityschedule;

import org.slf4j.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.rumakin.universityschedule.config.DatabaseConfig;

public class AppRunner {

    static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    public static void main(String[] args) {
        logger.info("Starting application.");
        ApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        ((AbstractApplicationContext) context).close();
        logger.info("Exiting application.");
    }

}
