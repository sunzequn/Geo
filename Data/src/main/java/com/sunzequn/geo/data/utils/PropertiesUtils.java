package com.sunzequn.geo.data.utils;


import com.sunzequn.geo.data.exception.ConfigException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Sloriac on 15/12/20.
 * <p>
 * 读取Properties配置文件的工具类
 */
public class PropertiesUtils {

    /**
     * 默认的配置文件路径
     */
    private static String defaultConfigFile = "config.properties";

    /**
     * 从配置文件中根据键获得值
     *
     * @param key 键
     * @return 如果键值对存在则返回值, 否则返回null
     */
    public static String getValue(String key) {
        Properties properties = new Properties();

        try {
            ClassLoader classLoader = PropertiesUtils.class.getClassLoader();
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
