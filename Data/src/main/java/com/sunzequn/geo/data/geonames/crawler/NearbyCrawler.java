package com.sunzequn.geo.data.geonames.crawler;

import com.sunzequn.geo.data.exception.HttpException;
import com.sunzequn.geo.data.geonames.bean.*;
import com.sunzequn.geo.data.geonames.bean.Error;
import com.sunzequn.geo.data.jena.Rdf;
import com.sunzequn.geo.data.utils.TimeUtils;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Sloriac on 16/1/21.
 */
public class NearbyCrawler {

    private static final int THREAD_NUM = 30;
    private static final int TIMEOUT = 8000;
    private static final int DURATION = 1000;
    private static final String PREFIX = "http://sws.geonames.org/";
    private static final String SUFFIX = "/nearby.rdf";

    private static GetProxy getProxy = new GetProxy();
    private static LinkedList<ProxyBean> proxyBeans = null;
    private static LinkedList<Integer> ids = new LinkedList<>();
    //    private static ResourceDao resourceDao = new ResourceDao("nearby_url");
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
                while (true) {
                    try {
                        HttpConnector httpConnector = new HttpConnector();
                        int id = getId();
                        if (id == 0) {
                            return;
                        }
//                            System.out.println(id);
                        String url = PREFIX + id + SUFFIX;
                        Response response = httpConnector.setUrl(url)
                                .setProxy(proxy.getHost(), proxy.getPort())
                                .getConnection().setTimeout(TIMEOUT).getContent();
//                            System.out.println(response.getCode() + ": " + url);
                        if (response.getCode() != 200) {
                            saveError(id);
                            proxy = getProxy();
                            continue;
                        }
                        String string = response.getContent().trim();
                        if (rdf.validate(string)) {

                            if (!rdf.isEmpty(string)) {
                                Nearby nearby = new Nearby(id, string, 0);
                                save(nearby);
                                System.out.println("+++ " + id);
                            } else {
                                Nearby nearby = new Nearby(id, null, 0);
                                save(nearby);
                                System.out.println("--- " + id);
                            }

                        } else {
                            proxy = getProxy();
                            System.out.println(string);
                            if (!string.contains("the hourly limit of 2000 credits for")) {
                                saveNoId(id);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
//                        break;
                    }
                }

            }, "thread" + i).start();
        }

    }

    private static synchronized void refreshProxy() {
        proxyBeans = getProxy.get666();
        System.out.println(proxyBeans);
    }

    private static synchronized ProxyBean getProxy() {
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

    private static void initIds() {
        Set<Integer> idset = new HashSet<>();
        for (int i = 3000000; i < 1000 * 10000; i++) {
            idset.add(i);
        }
        Set<Integer> handledIdset = new HashSet<>();
        int start = 0;
        while (true) {
            List<Nearby> nearbies = nearbyDao.getAll(start, 20000);
            if (nearbies == null || nearbies.size() == 0) {
                System.out.println("over");
                break;
            }
            for (int i = 0; i < nearbies.size(); i++) {
                Nearby nearby = nearbies.get(i);
                handledIdset.add(nearby.getId());
                if (i == nearbies.size() - 1) {
                    start = nearby.getId();
                }
            }
            System.out.println("success");
        }
        System.out.println("idset: " + idset.size());
        System.out.println("handledIdset: " + handledIdset.size());
        idset.removeAll(handledIdset);
        System.out.println("idset: " + idset.size());

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
