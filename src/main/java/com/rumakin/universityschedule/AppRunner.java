package com.rumakin.universityschedule;

import com.rumakin.universityschedule.dao.AcademicDegreeDao;
import com.rumakin.universityschedule.models.AcademicDegree;
import com.rumakin.universityschedule.util.DataSourceProvider;
import com.rumakin.universityschedule.util.JdbcTemplateConfig;
import com.rumakin.universityschedule.util.PropertiesLoader;

public class AppRunner {

    public static void main(String[] args) throws ClassNotFoundException {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        DataSourceProvider dataSource = new DataSourceProvider(propertiesLoader);
        JdbcTemplateConfig jdbcTemplate = new JdbcTemplateConfig(dataSource);
        AcademicDegreeDao degreeDao = new AcademicDegreeDao(jdbcTemplate.getJdbcTemplate());
        degreeDao.add(AcademicDegree.BACHELOR);
    }

}
