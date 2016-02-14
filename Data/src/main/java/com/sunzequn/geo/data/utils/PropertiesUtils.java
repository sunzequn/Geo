package com.sunzequn.geo.data.utils;


import com.sunzequn.geo.data.exception.ConfigException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Sloriac on 15/12/20.
 * <p>
 * 读取Properties配置文件的工具类
 */
public class PropertiesUtils {

    private static final String VIRTUOSO_CONF_FILE = "Data/src/main/resources/conf/virtuoso.properties";

    private Properties properties = new Properties();

    public PropertiesUtils(String filePath) {
        try {
            InputStream inputStream = new FileInputStream(new File(filePath));
            this.properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从配置文件中根据键获得值
     *
     * @param key 键
     * @return 如果键值对存在则返回值, 否则返回null
     */
    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
