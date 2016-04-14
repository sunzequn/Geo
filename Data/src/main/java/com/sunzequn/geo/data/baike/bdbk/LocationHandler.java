package com.sunzequn.geo.data.baike.bdbk;

import com.sunzequn.geo.data.baike.bdbk.UrlType;
import com.sunzequn.geo.data.baike.bdbk.UrlTypeDao;
import com.sunzequn.geo.data.baike.bdbk.UrlTypeLocation;
import com.sunzequn.geo.data.baike.bdbk.UrlTypeLocationDao;
import com.sunzequn.geo.data.baike.bdmap.BDDT;
import com.sunzequn.geo.data.baike.bdmap.LocationPull;
import com.sunzequn.geo.data.china.geo.ChinaCity;
import com.sunzequn.geo.data.china.geo.ChinaCityDao;
import com.sunzequn.geo.data.utils.ListUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sunzequn on 2016/4/12.
 */
public class LocationHandler {

    private static UrlTypeLocationDao urlTypeLocationDao = new UrlTypeLocationDao();
    private static UrlTypeDao urlTypeDao = new UrlTypeDao();
    private static LocationPull locationPull = new LocationPull();

    public static void main(String[] args) {
        getLocation();
//        xingzhengquhua();
    }

    private static void getLocation() {
        List<UrlType> urlTypes = urlTypeDao.getAll();
        System.out.println(urlTypes.size());
        List<UrlTypeLocation> locations = urlTypeLocationDao.getAllUrl();
        System.out.println(locations.size());
        Set<String> urls = new HashSet<>();
        for (UrlTypeLocation location : locations) {
            urls.add(location.getUrl());
        }
        List<UrlTypeLocation> urlTypeLocations = new ArrayList<>();
        for (UrlType urlType : urlTypes) {
            if (urls.contains(urlType.getUrl())) {
                System.out.println("1");
                continue;
            }
            try {
                BDDT bddt = locationPull.getLngLat(urlType.getTitle());
                if (bddt.isValid()) {
                    UrlTypeLocation urlTypeLocation = new UrlTypeLocation(urlType.getUrl(), urlType.getType(), urlType.getTitle());
                    urlTypeLocation.setLng(bddt.getLng());
                    urlTypeLocation.setLat(bddt.getLat());
                    urlTypeLocation.setConfidence(bddt.getConfidence());
                    urlTypeLocation.setLevel(bddt.getLevel());
                    urlTypeLocation.setPrecise(bddt.getPrecise());
                    System.out.println(urlTypeLocation);
                    urlTypeLocations.add(urlTypeLocation);
                    if (urlTypeLocations.size() == 100) {
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
