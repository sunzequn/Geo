package com.sunzequn.geo.data.geonames.crawler;

import com.sunzequn.geo.data.exception.HttpException;
import com.sunzequn.geo.data.geonames.missingdata.Content;
import com.sunzequn.geo.data.geonames.missingdata.ContentDao;
import com.sunzequn.geo.data.geonames.missingdata.Resource;
import com.sunzequn.geo.data.geonames.missingdata.ResourceDao;
import com.sunzequn.geo.data.utils.StringUtils;
import com.sunzequn.geo.data.utils.TimeUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sloriac on 16/1/21.
 */
public class Main {

    private static final int THREAD_NUM = 20;
    private static final int TIMEOUT = 10000;
    private static final int DURATION = 15000;
    private static final String PREFIX = "http://sws.geonames.org/";
    private static final String SUFFIX = "/nearby.rdf";

    private static GetProxy getProxy = new GetProxy();
    private static ProxyBean proxyBean = null;
    private static LinkedList<Resource> resources = new LinkedList<>();
    private static ResourceDao resourceDao = new ResourceDao("contains_url");

    public static void main(String[] args) throws InterruptedException {

        while (true) {
            refreshProxy();
            TimeUtils.start();
            for (int i = 0; i < THREAD_NUM; i++) {
                new Thread(() -> {
                    ContentDao contentDao = new ContentDao("contains");
                    while (true) {
                        try {
                            HttpConnector httpConnector = new HttpConnector();
                            int id = getId();
                            String url = PREFIX + id + SUFFIX;
                            String string = httpConnector.setUrl(url)
                                    .setProxy(getHost(), getPort())
                                    .getConnection().setTimeout(TIMEOUT).getContent();
                            if (string.trim().startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>")) {
                                Content content = new Content(id, string);
                                contentDao.save(content);
                            } else {
                                throw new HttpException("返回文件不正确");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            contentDao.close();
                            break;
                        }
                    }
                }, "thread" + i).start();
            }
            if (TimeUtils.duration() <= DURATION) {
                TimeUtils.print();
                Thread.sleep(DURATION);
            }
        }
    }

    private static void refreshProxy() {
        proxyBean = getProxy.get();
        System.out.println(proxyBean);
    }

    private static int getPort() {
        return proxyBean.getPort();
    }

    private static String getHost() {
        return proxyBean.getHost();
    }

    private static synchronized int getId() {
        if (resources.size() < THREAD_NUM + 10) {
            resources = new LinkedList<>(resourceDao.getUnvisited(1000));
        }
        return resources.pop().getId();
    }
}
