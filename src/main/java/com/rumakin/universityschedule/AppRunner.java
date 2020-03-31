package com.rumakin.universityschedule;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.rumakin.universityschedule.dao.AuditoriumDao;
import com.rumakin.universityschedule.dao.BuildingDao;
import com.rumakin.universityschedule.models.Auditorium;
import com.rumakin.universityschedule.models.Building;
import com.rumakin.universityschedule.service.AuditoriumService;
import com.rumakin.universityschedule.service.BuildingService;
import com.rumakin.universityschedule.util.*;

public class AppRunner {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
//        AuditoriumDao dao = new AuditoriumDao(context.getBean(JdbcTemplate.class), context.getBean(BuildingDao.class));
//        Auditorium au = new Auditorium(1, 1, new Building("a","b"));
//        dao.findAll();
        context.close();
    }

}
