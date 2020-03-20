package com.rumakin.universityschedule;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.rumakin.universityschedule.dao.AuditoriumDao;
import com.rumakin.universityschedule.service.AuditoriumService;
import com.rumakin.universityschedule.service.BuildingService;
import com.rumakin.universityschedule.util.*;

public class AppRunner {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
        context.close();
    }

}
