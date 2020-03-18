package com.rumakin.universityschedule.util;

import java.io.*;
import java.util.Properties;

public final class PropertiesConfiguration {

    private static final String PROPERTIES_PATH = "src\\main\\resources\\config.properties";

    static Properties properties = new Properties();

    public static Properties loadProperties() {
        try (FileInputStream stream = new FileInputStream(PROPERTIES_PATH)) {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}
