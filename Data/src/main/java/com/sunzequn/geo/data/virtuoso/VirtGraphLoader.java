package com.sunzequn.geo.data.virtuoso;

import com.sunzequn.geo.data.exception.ConfigException;
import com.sunzequn.geo.data.utils.PropertiesUtils;
import virtuoso.jena.driver.VirtGraph;

/**
 * Created by Sloriac on 16/2/14.
 * <p>
 * 根据配置文件连接Virtuoso数据库,加载VirtGraph.
 */
public class VirtGraphLoader {

    /**
     * 单例模式
     */
    private static final VirtGraphLoader ourInstance = new VirtGraphLoader();

    /**
     * 配置文件地址
     */
    private static final String VIRTUOSO_CONF_FILE = "Data/src/main/resources/conf/virtuoso.properties";

    /**
     * Virtuoso图
     */
    private VirtGraph virtGraph = null;


    public static VirtGraphLoader getInstance() {
        return ourInstance;
    }

    private VirtGraphLoader() {
        PropertiesUtils properties = new PropertiesUtils(VIRTUOSO_CONF_FILE);
        try {
            String serverHost = properties.getValue("ServerHost");
            String serverPort = properties.getValue("ServerPort");
            String userName = properties.getValue("UserName");
            String password = properties.getValue("Password");
            if (serverHost != null && serverPort != null && userName != null && password != null) {
                String url = "jdbc:virtuoso://" + serverHost + ":" + serverPort;
                virtGraph = new VirtGraph(url, userName, password);
            } else {
                throw new ConfigException("Virtuoso配置文件出错!");
            }
        } catch (ConfigException e) {
            e.printStackTrace();
        }
    }

    public VirtGraph getVirtGraph() {
        return virtGraph;
    }
}
