package com.rumakin.universityschedule.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    
    private static final String PROPERTIES_PATH = "src\\main\\resources\\config.properties";

    private Properties properties;

    public PropertiesLoader() {
        properties = new Properties();
        try (FileInputStream stream = new FileInputStream(PROPERTIES_PATH)) {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String loadDriver() {
        return properties.getProperty("db.driver");
    }

    public String loadLogin() {
        return properties.getProperty("db.login");
    }

    public String loadPassword() {
        return properties.getProperty("db.password");
    }

    public String loadHost() {
        return properties.getProperty("db.host");
    }

    public String loadTables() {
        return properties.getProperty("db.tables");
    }

}
