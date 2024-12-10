package com.example.library;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final String CONFIG_FILE = "/resources/config.properties";

    public static String getGoogleApiKey() {
        try (InputStream input = Config.class.getResourceAsStream(CONFIG_FILE)) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return null;
            }
            prop.load(input);
            return prop.getProperty("google.api.key");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}