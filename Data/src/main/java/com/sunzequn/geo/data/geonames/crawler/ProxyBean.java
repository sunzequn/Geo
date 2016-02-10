package com.sunzequn.geo.data.geonames.crawler;

import java.util.LinkedList;

/**
 * Created by Sloriac on 16/1/21.
 */
public class ProxyBean {

    private String host;
    private int port;
    private int visitedNum = 80;

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

    public int getVisitedNum() {
        return visitedNum;
    }

    public void setVisitedNum(int visitedNum) {
        this.visitedNum = visitedNum;
    }

    public void desc(){
        this.visitedNum--;
    }

    @Override
    public String toString() {
        return "ProxyBean{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", visitedNum=" + visitedNum +
                '}';
    }

    public static void main(String[] args) throws InterruptedException {
        GetProxy getProxy = new GetProxy();
        LinkedList<ProxyBean> proxyBeans = getProxy.get666();
        ProxyBean proxyBean = proxyBeans.getFirst();
        proxyBean.desc();
        System.out.println(proxyBean);
        System.out.println(proxyBeans.size());
        System.out.println(proxyBeans.pop());
        System.out.println(proxyBeans.size());

    }
}
