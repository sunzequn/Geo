package com.sunzequn.geo.data.crawler.proxy;

import java.util.LinkedList;

/**
 * Created by Sloriac on 16/2/11.
 */
public class ProxyHandler {

    private GetProxy getProxy = new GetProxy();
    private LinkedList<ProxyBean> proxyBeans = new LinkedList<>();

    public ProxyHandler() {

    }

    public synchronized void refreshProxy() {
        proxyBeans = getProxy.get666();
        System.out.println(proxyBeans);
    }

    public synchronized ProxyBean getProxy() {
        if (proxyBeans.size() == 0) {
            refreshProxy();
        }
        ProxyBean proxyBean = proxyBeans.getFirst();
        if (proxyBean.getVisitedNum() == 0) {
            proxyBeans.pop();
            return getProxy();
        } else {
            proxyBean.desc();
            return proxyBean;
        }
    }
}
