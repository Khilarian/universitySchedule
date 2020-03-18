package com.rumakin.universityschedule.util;

import java.util.*;

public class PropertiesLoader {

    private Properties properties;

    public PropertiesLoader(Properties properties) {
        this.properties = properties;
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
