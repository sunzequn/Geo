//package com.sunzequn.geo.data.link;
//
//import com.sunzequn.geo.data.climate.bean.Place;
//import com.sunzequn.geo.data.climate.bean.Region;
//import com.sunzequn.geo.data.climate.dao.PlaceDao;
//import com.sunzequn.geo.data.climate.dao.RegionDao;
//import com.sunzequn.geo.data.geonames.bean.Countryinfo;
//import com.sunzequn.geo.data.geonames.bean.Geoname;
//import com.sunzequn.geo.data.geonames.dao.CountryInfoDao;
//import com.sunzequn.geo.data.geonames.dao.GeonameDao;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * Created by sloriac on 16-2-17.
// */
//public class PlaceLinkThread {
//
//    private static LinkDao countryLinkDao = new LinkDao("country_link");
//    private static LinkedList<LinkBean> linkedCountries = new LinkedList(countryLinkDao.getAll1());
//
//    public static void main(String[] args) {
//
//        for (int i = 0; i < linkedCountries.size(); i++) {
//            new Thread(() -> {
//
//                CountryInfoDao countryInfoDao = new CountryInfoDao();
//                LinkBean linkedCountry = getLinkedCountry();
//                Countryinfo countryinfo = countryInfoDao.getById(linkedCountry.getGeonameid());
//                PlaceLink placeLink = new PlaceLink(countryinfo.getIso_alpha2());
//
//                List<Place> places = placeLink.getClimatePlaces(linkedCountry.getClimateid());
//                System.out.println(Thread.currentThread().getSubtitle() + ": place num: " + places.size());
//                int num = 0;
//                num += placeLink.calculateFcode(countryinfo.getIso_alpha2(), "ADM1", places);
//                num += placeLink.calculateFcode(countryinfo.getIso_alpha2(), "ADM2", places);
//                num += placeLink.calculateFcode(countryinfo.getIso_alpha2(), "ADM3", places);
//                num += placeLink.calculateFcode(countryinfo.getIso_alpha2(), "ADM4", places);
//                num += placeLink.calculateFcode(countryinfo.getIso_alpha2(), "ADM5", places);
//                num += placeLink.calculateFcode(countryinfo.getIso_alpha2(), "ADM3H", places);
//                num += placeLink.calculateFcode(countryinfo.getIso_alpha2(), "ADM4H", places);
//                num += placeLink.calculateFcode(countryinfo.getIso_alpha2(), "ADMD", places);
//                num += placeLink.calculateFclass(countryinfo.getIso_alpha2(), "P", places);
//
//                System.out.println(Thread.currentThread().getSubtitle() + ": matched num: " + num);
//                System.out.println(Thread.currentThread().getSubtitle() + ": 差值: " + (num - places.size()));
//
//            }, "thread" + i).start();
//        }
//    }
//
//    private static synchronized LinkBean getLinkedCountry() {
//        if (linkedCountries.size() > 0) {
//            return linkedCountries.pop();
//        }
//        return null;
//    }
//
//}
