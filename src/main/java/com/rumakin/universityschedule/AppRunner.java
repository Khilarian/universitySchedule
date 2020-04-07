package com.rumakin.universityschedule;

import org.slf4j.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.rumakin.universityschedule.config.ApplicationContextConfiguration;
import com.rumakin.universityschedule.dao.AuditoriumDao;
import com.rumakin.universityschedule.dao.BuildingDao;
import com.rumakin.universityschedule.models.Auditorium;
import com.rumakin.universityschedule.models.Building;
import com.rumakin.universityschedule.util.*;

public class AppRunner {
    static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    public static void main(String[] args) {
        logger.info("Starting application.");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
        
//        AuditoriumDao dao = new AuditoriumDao(context.getBean(JdbcTemplate.class), context.getBean(BuildingDao.class));
//        Auditorium au = new Auditorium(1, 1, new Building("a","b"));
//        dao.findAll();
        context.close();
        logger.info("Exiting application.");
    }

}
