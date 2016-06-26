package com.sunzequn.geo.data.baike.bdbk;

import com.sunzequn.geo.data.baike.bdmap.BDDT;
import com.sunzequn.geo.data.baike.bdmap.LocationPull;
import com.sunzequn.geo.data.china.geo.ChinaCity;
import com.sunzequn.geo.data.china.geo.ChinaCityDao;
import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.StringUtils;

import java.util.*;

/**
 * Created by sunzequn on 2016/4/12.
 */
public class LocationHandler {

    private static UrlTypeLocationDao urlTypeLocationDao = new UrlTypeLocationDao();
    private static UrlTypeDao urlTypeDao = new UrlTypeDao("url_type_zhengli_all_ifchina");
    private static LocationPull locationPull = new LocationPull();
    private static List<UrlType> urlTypes;
    private static LinkedList<UrlType> urlTypeLinkedList;
    private static List<UrlTypeLocation> urlTypeLocations = new ArrayList<>();

    public static void main(String[] args) {
        init();
        for (int i = 0; i < 1; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep((int) (Math.random() * 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    UrlType urlType = getUrlType();
                    if (urlType == null) {
                        return;
                    }
                    try {
                        String key = urlType.getTitle();
                        if (!StringUtils.isNullOrEmpty(urlType.getSubtitle())) {
                            key = urlType.getSubtitle() + key;
                        }
                        BDDT bddt = locationPull.getLngLat(key);
                        System.out.println(bddt);
                        if (bddt.isValid()) {
                            UrlTypeLocation urlTypeLocation = new UrlTypeLocation(urlType.getUrl(), urlType.getType(), urlType.getTitle());
                            urlTypeLocation.setLng(bddt.getLng());
                            urlTypeLocation.setLat(bddt.getLat());
                            urlTypeLocation.setConfidence(bddt.getConfidence());
                            urlTypeLocation.setLevel(bddt.getLevel());
                            urlTypeLocation.setPrecise(bddt.getPrecise());
                            System.out.println(urlTypeLocation);
                            addLocation(urlTypeLocation);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }, "thread" + i).start();
        }
    }

    private static void init() {
        urlTypes = urlTypeDao.getAll(2);
        System.out.println(urlTypes.size());
        urlTypeLinkedList = new LinkedList<>();
        for (UrlType urlType : urlTypes) {
            if (!StringUtils.isNullOrEmpty(urlType.getSubtitle())) {
                urlTypeLinkedList.add(urlType);
            }
        }
        System.out.println(urlTypeLinkedList.size());

    }

    private static synchronized void addLocation(UrlTypeLocation location) {
        urlTypeLocations.add(location);
        if (urlTypeLocations.size() == 1000) {
            urlTypeLocationDao.addBatch(urlTypeLocations);
            urlTypeLocations = new ArrayList<>();
        }
    }

    private static synchronized UrlType getUrlType() {

        if (urlTypeLinkedList.size() > 0) {
            return urlTypeLinkedList.pop();
        }
        return null;
    }

    private static void getLocation() {
        List<UrlType> urlTypes = urlTypeDao.getAll(2);
        System.out.println(urlTypes.size());
//        List<UrlTypeLocation> locations = urlTypeLocationDao.getAllUrl();
//        System.out.println(locations.size());
//        Set<String> urls = new HashSet<>();
//        for (UrlTypeLocation location : locations) {
//            urls.add(location.getUrl());
//        }
        List<UrlTypeLocation> urlTypeLocations = new ArrayList<>();
//        int visited = 0;
        for (UrlType urlType : urlTypes) {
//            if (urls.contains(urlType.getUrl())) {
//                visited++;
//                System.out.println(visited);
//                continue;
//            }
            try {
                String key = urlType.getTitle();
                if (!StringUtils.isNullOrEmpty(urlType.getSubtitle())) {
                    key = urlType.getSubtitle() + key;
                }
                BDDT bddt = locationPull.getLngLat(key);
                if (bddt.isValid()) {
                    UrlTypeLocation urlTypeLocation = new UrlTypeLocation(urlType.getUrl(), urlType.getType(), urlType.getTitle());
                    urlTypeLocation.setLng(bddt.getLng());
                    urlTypeLocation.setLat(bddt.getLat());
                    urlTypeLocation.setConfidence(bddt.getConfidence());
                    urlTypeLocation.setLevel(bddt.getLevel());
                    urlTypeLocation.setPrecise(bddt.getPrecise());
                    System.out.println(urlTypeLocation);
                    urlTypeLocations.add(urlTypeLocation);
                    if (urlTypeLocations.size() == 1000) {
                        urlTypeLocationDao.addBatch(urlTypeLocations);
                        urlTypeLocations = new ArrayList<>();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
//        if (urlTypeLocations.size() > 0){
//            urlTypeLocationDao.addBatch(urlTypeLocations);
//        }
    }

    private static void xingzhengquhua() {
        Set<String> urls = new HashSet<>();
        ChinaCityDao chinaCityDao = new ChinaCityDao();
        List<ChinaCity> chinaCities = chinaCityDao.getAll();
        for (ChinaCity chinaCity : chinaCities) {
            List<UrlType> urlTypes = urlTypeDao.getByTitle(chinaCity.getName(), chinaCity.getShortname());
            if (!ListUtils.isEmpty(urlTypes)) {
                for (UrlType urlType : urlTypes) {
                    UrlTypeLocation urlTypeLocation = new UrlTypeLocation(urlType.getUrl(), urlType.getType(), urlType.getTitle());
                    urlTypeLocation.setLng(chinaCity.getLng());
                    urlTypeLocation.setLat(chinaCity.getLat());
                    urls.add(urlType.getTitle());
                    System.out.println(urlTypeLocation);
//                    urlTypeLocationDao.add(urlTypeLocation);
                }
            }

        }
        System.out.println(urls.size());
    }

}
