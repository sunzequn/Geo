package com.sunzequn.geo.data.geonames.crawler;

import com.sunzequn.geo.data.exception.HttpException;
import com.sunzequn.geo.data.geonames.bean.Content;
import com.sunzequn.geo.data.geonames.bean.ContentDao;
import com.sunzequn.geo.data.geonames.bean.Resource;
import com.sunzequn.geo.data.geonames.bean.ResourceDao;
import com.sunzequn.geo.data.jena.Rdf;
import com.sunzequn.geo.data.utils.TimeUtils;

import java.util.LinkedList;

/**
 * Created by Sloriac on 16/1/21.
 */
public class Neighbours {

    private static final int THREAD_NUM = 1;
    private static final int TIMEOUT = 7000;
    private static final int DURATION = 5000;
    private static final String PREFIX = "http://sws.geonames.org/";
    private static final String SUFFIX = "/neighbours.rdf";

    private static GetProxy getProxy = new GetProxy();
    private static LinkedList<ProxyBean> proxyBeans = null;
    private static LinkedList<Resource> resources = new LinkedList<>();
    private static ResourceDao resourceDao = new ResourceDao("neighbours_url");
    private static ContentDao contentDao = new ContentDao("neighbours");

    public static void main(String[] args) throws InterruptedException {
        refreshProxy();
        while (true) {
            ProxyBean proxy = getProxy();
            TimeUtils timeUtils = new TimeUtils();
            timeUtils.start();
            for (int i = 0; i < THREAD_NUM; i++) {
                Rdf rdf = new Rdf();
                new Thread(() -> {
                    while (true) {
                        try {
                            HttpConnector httpConnector = new HttpConnector();
                            int id = getId();
                            if (id == 0) {
                                return;
                            }
                            System.out.println(id);
                            String url = PREFIX + id + SUFFIX;
                            Response response = httpConnector.setUrl(url)
                                    .setProxy(proxy.getHost(), proxy.getPort())
                                    .getConnection().setTimeout(TIMEOUT).getContent();
                            System.out.println(response.getCode() + ": " + url);
                            if (response.getCode() != 200) {
                                update(id, 2);
                                continue;
                            }
                            String string = response.getContent().trim();
                            if (rdf.validate(string)) {
                                Content content = new Content(id, string);
                                update(id, 1);
                                save(content);
                            } else {
                                System.out.println(string);
                                throw new HttpException("返回文件不正确");
                            }
                        } catch (Exception e) {
//                            e.printStackTrace();
                            break;
                        }
                    }
                }, "thread" + i).start();
            }
            if (timeUtils.duration() <= DURATION) {
                timeUtils.end();
                timeUtils.print();
                Thread.sleep(DURATION);
            }
        }
    }

    private static void refreshProxy() throws InterruptedException {
        proxyBeans = getProxy.get666();
        System.out.println(proxyBeans);
    }

    private static ProxyBean getProxy() throws InterruptedException {
        if (proxyBeans.size() == 0) {
            refreshProxy();
        }
        return proxyBeans.pop();
    }

    private static synchronized int getId() {
        if (resources.size() < THREAD_NUM + 10) {
            resources = new LinkedList<>(resourceDao.getUnvisited());
            if (resources.size() == 0) {
                return 0;
            }
        }
        return resources.pop().getId();
    }

    private static synchronized void save(Content content) {
        contentDao.save(content);
    }

    private static synchronized void update(int id, int status) {
        resourceDao.update(id, status);
    }

}
