package com.sunzequn.geo.data.geonames.crawler;

import com.sunzequn.geo.data.exception.HttpException;
import com.sunzequn.geo.data.geonames.bean.*;
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

    private static final int THREAD_NUM = 40;
    private static final int TIMEOUT = 8000;
    private static final int DURATION = 1000 * 10;
    private static final String PREFIX = "http://sws.geonames.org/";
    private static final String SUFFIX = "/nearby.rdf";

    private static GetProxy getProxy = new GetProxy();
    private static LinkedList<ProxyBean> proxyBeans = null;
    private static LinkedList<Integer> ids = new LinkedList<>();
    private static ResourceDao resourceDao = new ResourceDao("nearby_url");
    private static NearbyDao nearbyDao = new NearbyDao();

    public static void main(String[] args) throws InterruptedException {
        initIds();
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
                            if (rdf.validate(string) && (!rdf.isEmpty(string))) {
                                Nearby nearby = new Nearby(id, string, 0);
                                update(id, 1);
                                save(nearby);
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

            Thread.sleep(DURATION);

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

    private static void initIds(){
        Set<Integer> idset = new HashSet<>();
        for (int i = 1; i < 1000 * 10000; i++){
            idset.add(i);
        }
        Set<Integer> handledIdset = new HashSet<>();
        int start = 0;
        while (true){
            List<Nearby> nearbies = nearbyDao.getAll(start, 20000);
            if (nearbies == null || nearbies.size() == 0){
                System.out.println("over");
                break;
            }
            for (int i = 0; i < nearbies.size(); i++){
                Nearby nearby = nearbies.get(i);
                handledIdset.add(nearby.getId());
                if (i == nearbies.size() - 1){
                    start = nearby.getId();
                }
            }
            System.out.println("success");
        }
        idset.removeAll(handledIdset);
        ids.addAll(idset);
        System.out.println(handledIdset.size());
        System.out.println(ids.size());
        System.out.println(idset.size());
    }

    private static synchronized int getId() {
        return ids.pop();
    }

    private static synchronized void save(Nearby nearby) {
        nearbyDao.save(nearby);
    }

    private static synchronized void update(int id, int status) {
        resourceDao.update(id, status);
    }

}
