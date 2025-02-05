package com.veeva.config;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class ConfigProperties {

    private Properties properties = new Properties();

    public void loadProperties(String filename) {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(filename)) {
            if (input == null) {
                System.out.println("Sorry, unable to find " + filename);
                return;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getProperty(String key,String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}

