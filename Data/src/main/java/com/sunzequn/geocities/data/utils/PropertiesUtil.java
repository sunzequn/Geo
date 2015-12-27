package com.sunzequn.geocities.data.utils;


import com.sunzequn.geocities.data.exception.ConfigException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Sloriac on 15/12/20.
 * <p>
 * The util class for reading properties files.
 */
public class PropertiesUtil {

    /**
     * The name of the default config file.
     */
    private static String defaultConfigFile = "config.properties";

    /**
     * Get the value by it`s key from the default config.
     *
     * @param key The value`s key.
     * @return the value if it is existed otherwise null.
     */
    public static String getValue(String key) {
        Properties properties = new Properties();

        try {
            ClassLoader classLoader = PropertiesUtil.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(defaultConfigFile);
            if (inputStream == null) {
                throw new ConfigException("No config file founded.");
            }
            properties.load(inputStream);
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
