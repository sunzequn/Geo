package com.sunzequn.geo.data.geonamesplus;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * 
 * @author sunzequn
 * 
 * 拓展GeoNames的数据源
 */
public class GnextDataSource {
	
    private static final String DEFAULT_Gnext_CONF_FILE = "config/gnext.properties";
    private Connection connection = null;
    private int initialPoolSize = 10;

    public GnextDataSource() {
		Properties properties = new Properties();
		InputStream in;
		try {
			in = new FileInputStream(DEFAULT_Gnext_CONF_FILE);
			properties.load(in);
			initialPoolSize = Integer.parseInt(properties.getProperty("initialPoolSize"));
            Class.forName(properties.getProperty("classDriver"));
            connection = DriverManager.getConnection(properties.getProperty("jdbcUrl"), properties.getProperty("user"), properties.getProperty("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

	public int getInitialPoolSize() {
		return initialPoolSize;
	}
}
