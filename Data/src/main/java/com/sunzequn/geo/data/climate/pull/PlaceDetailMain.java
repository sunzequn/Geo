package com.sunzequn.geo.data.climate.pull;

import com.sunzequn.geo.data.climate.bean.Place;
import com.sunzequn.geo.data.climate.bean.PlaceDetail;
import com.sunzequn.geo.data.climate.bean.PlaceWebWrapper;
import com.sunzequn.geo.data.climate.dao.PlaceDao;
import com.sunzequn.geo.data.climate.dao.PlaceDetailDao;
import com.sunzequn.geo.data.climate.pull.parser.RegionParser;
import com.sunzequn.geo.data.crawler.proxy.ProxyBean;
import com.sunzequn.geo.data.crawler.proxy.ProxyHandler;
import com.sunzequn.geo.data.geonames.crawler.HttpConnector;
import com.sunzequn.geo.data.geonames.crawler.Response;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Sloriac on 16/3/3.
 */
public class PlaceDetailMain {
    private static final int THREAD_NUM = 10;
    private static final int TIMEOUT = 7000;
    private static final String PREFIX = "http://en.climate-data.org";
    private static LinkedList<String> urls = null;
    private static ProxyHandler proxyHandler = new ProxyHandler();
    private static PlaceDetailDao placeDetailDao = new PlaceDetailDao();
    private static PlaceDao placeDao = new PlaceDao("climate_seed_place");
    private static PlaceDao placeFromCountryDao = new PlaceDao("climate_seed_place_from_country");

    public static void main(String[] args) {
        initUrls();
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(() -> {
                ProxyBean proxy = getProxy();
                while (true) {
                    try {
                        HttpConnector httpConnector = new HttpConnector();
                        String url = getUri();
                        if (url == null) {
                            System.out.println(Thread.currentThread().getName() + "is over");
                            return;
                        }
                        Response response = httpConnector.setUrl(url)
                                .setProxy(proxy.getHost(), proxy.getPort())
                                .getConnection().setTimeout(TIMEOUT).getContent();

                        if (response.getCode() != 200) {
                            addUrl(url);
                            proxy = getProxy();
                            continue;
                        }
                        String string = response.getContent().trim();
                        PlaceDetail placeDetail = new PlaceDetail(url, string);
                        savePlace(placeDetail);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }, "thread" + i).start();
        }
    }

    private static synchronized ProxyBean getProxy() {
        return proxyHandler.getProxy();
    }

    private static void initUrls() {
        List<Place> places = placeDao.getAllUrls();
        System.out.println(places.size());
        List<Place> placesFromCountry = placeFromCountryDao.getAllUrls();
        System.out.println(placesFromCountry.size());

        Set<String> placeSet = new HashSet<>();
        for (Place place : places) {
            placeSet.add(place.getUrl());
        }
        for (Place place : placesFromCountry) {
            placeSet.add(place.getUrl());
        }
        System.out.println(placeSet.size());
        urls = new LinkedList<>(placeSet);
        System.out.println(urls.size());
    }

    private static synchronized String getUri() {
        if (urls.size() > 0) {
            return PREFIX + urls.pop();
        }
        return null;
    }

    private static synchronized void addUrl(String url) {
        urls.add(url);
    }

    private static synchronized void savePlace(PlaceDetail placeDetail) {
        placeDetailDao.save(placeDetail);
    }
}
