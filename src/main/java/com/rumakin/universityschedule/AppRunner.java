package com.rumakin.universityschedule;

import org.slf4j.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.rumakin.universityschedule.config.ApplicationContextConfiguration;
public class AppRunner {
    static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    public static void main(String[] args) {
        logger.info("Starting application.");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
        context.close();
        logger.info("Exiting application.");
    }

}
