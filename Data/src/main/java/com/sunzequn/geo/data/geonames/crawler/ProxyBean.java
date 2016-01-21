package com.sunzequn.geo.data.geonames.crawler;

/**
 * Created by Sloriac on 16/1/21.
 */
public class ProxyBean {

    private String host;
    private int port;

    public ProxyBean() {
    }

    public ProxyBean(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Proxy{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
