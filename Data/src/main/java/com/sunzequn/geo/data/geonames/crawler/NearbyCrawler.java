package com.sunzequn.geo.data.geonames.crawler;

import com.sunzequn.geo.data.crawler.proxy.GetProxy;
import com.sunzequn.geo.data.crawler.proxy.ProxyBean;
import com.sunzequn.geo.data.crawler.proxy.ProxyHandler;
import com.sunzequn.geo.data.geonames.filebean.*;
import com.sunzequn.geo.data.geonames.filebean.Error;
import com.sunzequn.geo.data.jena.Rdf;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Sloriac on 16/1/21.
 */
public class NearbyCrawler {

    private static final int THREAD_NUM = 80;
    private static final int TIMEOUT = 6000;
    private static final int DURATION = 1000;
    private static final String PREFIX = "http://sws.geonames.org/";
    private static final String SUFFIX = "/nearby.rdf";

    private static GetProxy getProxy = new GetProxy();
    private static ProxyHandler proxyHandler = new ProxyHandler();
    private static LinkedList<ProxyBean> proxyBeans = null;
    private static LinkedList<Integer> ids = new LinkedList<>();
    private static NearbyDao nearbyDao = new NearbyDao();
    private static NoIdDao noIdDao = new NoIdDao();
    private static ErrorDao errorDao = new ErrorDao();

    public static void main(String[] args) throws InterruptedException {
        initIds();
        refreshProxy();
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(() -> {
                Rdf rdf = new Rdf();
                ProxyBean proxy = getProxy();
                NearbyDao threadDao = new NearbyDao();
                while (true) {
                    try {
                        HttpConnector httpConnector = new HttpConnector();
                        int id = getId();
                        if (id == 0) {
                            return;
                        }
                        String url = PREFIX + id + SUFFIX;
                        Response response = httpConnector.setUrl(url)
                                .setProxy(proxy.getHost(), proxy.getPort())
                                .getConnection().setTimeout(TIMEOUT).getContent();
                        if (response.getCode() != 200) {
//                            saveError(id);
                            addId(id);
                            proxy = getProxy();
                            continue;
                        }
                        String string = response.getContent().trim();
                        if (rdf.validate(string)) {

                            if (!rdf.isEmpty(string)) {
                                Nearby nearby = new Nearby(id, string, 0);
//                                save(nearby);
                                threadDao.save(nearby);
                                System.out.println("+++ " + id);
                            } else {
                                Nearby nearby = new Nearby(id, null, 0);
//                                save(nearby);
                                threadDao.save(nearby);
                                System.out.println("--- " + id);
                            }

                        } else {
                            proxy = getProxy();
                            System.out.println(string);
                            if (!string.contains("the hourly limit of 2000 credits for")) {
                                saveNoId(id);
                            } else {
                                addId(id);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }, "thread" + i).start();
        }

    }

    private static synchronized void addId(int id) {
        ids.add(id);
    }

    private static synchronized void refreshProxy() {
        proxyHandler.refreshProxy();
    }

    private static synchronized ProxyBean getProxy() {
        return proxyHandler.getProxy();
    }

    private static void initIds() {
        Set<Integer> idset = new HashSet<>();
        for (int i = 10210000; i <= 10226959; i++) {
            idset.add(i);
        }
        Set<Integer> handledIdset = new HashSet<>();
        List<Nearby> nearbies = nearbyDao.getAll(10209999, 40000);
        for (int i = 0; i < nearbies.size(); i++) {
                Nearby nearby = nearbies.get(i);
                handledIdset.add(nearby.getId());
        }

//        Set<Integer> handledIdset = new HashSet<>();
//        int start = 0;
//        while (true) {
//            List<Nearby> nearbies = nearbyDao.getAll(start, 40000);
//            if (nearbies == null || nearbies.size() == 0) {
//                System.out.println("over");
//                break;
//            }
//            for (int i = 0; i < nearbies.size(); i++) {
//                Nearby nearby = nearbies.get(i);
//                handledIdset.add(nearby.getUrl());
//                if (i == nearbies.size() - 1) {
//                    start = nearby.getUrl();
//                }
//            }
//            System.out.println("success");
//        }

        System.out.println("idset: " + idset.size());
        System.out.println("handledIdset: " + handledIdset.size());
        idset.removeAll(handledIdset);
        System.out.println("idset: " + idset.size());
//
        List<NoId> noIds = noIdDao.getAll();
        if (noIds != null || noIds.size() != 0) {
            Set<Integer> nosets = new HashSet<>();
            for (NoId noId : noIds) {
                nosets.add(noId.getId());
            }
            System.out.println("nosets: " + nosets.size());
            idset.removeAll(nosets);
            System.out.println("idset: " + idset.size());
        }

        ids.addAll(idset);
        System.out.println("ids: " + ids.size());
    }

    private static synchronized int getId() {
        if (ids.size() == 0) {
            System.out.println("over!");
            return 0;
        }
        return ids.pop();
    }

    private static synchronized void saveNoId(int id) {
        noIdDao.save(new NoId(id));
    }

    private static synchronized void saveError(int id) {
        errorDao.save(new Error(id));
    }

    private static synchronized void save(Nearby nearby) {
        nearbyDao.save(nearby);
    }

//    private static synchronized void update(int id, int status) {
//        resourceDao.update(id, status);
//    }

}
