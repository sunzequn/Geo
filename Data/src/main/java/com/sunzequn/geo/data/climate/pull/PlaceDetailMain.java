package com.sunzequn.geo.data.climate.pull;

import com.sunzequn.geo.data.climate.bean.Place;
import com.sunzequn.geo.data.climate.dao.PlaceDao;
import com.sunzequn.geo.data.crawler.proxy.ProxyBean;
import com.sunzequn.geo.data.crawler.proxy.ProxyHandler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Sloriac on 16/3/3.
 */
public class PlaceDetailMain {
    private static final int THREAD_NUM = 60;
    private static final int TIMEOUT = 7000;
    private static final String PREFIX = "http://en.climate-data.org/";
    private static final String SUFFIX = "/";
    private static LinkedList<String> urls = null;
    private static ProxyHandler proxyHandler = new ProxyHandler();
    private static PlaceDao placeDao = new PlaceDao("climate_seed_place");
    private static PlaceDao placeFromCountryDao = new PlaceDao("climate_seed_place_from_country");

    public static void main(String[] args) {
        initUrls();
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
            return urls.pop();
        }
        return null;
    }

    private static synchronized void addUrl(String url) {
        urls.add(url);
    }
}
