package com.sunzequn.geo.data.link;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by sloriac on 16-2-17.
 */
public class PlaceLinkMain {

    private static LinkDao countryLinkDao = new LinkDao("country_link");
    public final static ForkJoinPool mainPool = new ForkJoinPool();

//    public static void main(String[] args) {
//        List<LinkBean> linkedCountries = countryLinkDao.getAll();
//        int size = linkedCountries.size();
//
//        PlaceLinkTask placeLinkTask = new PlaceLinkTask(0, size-1, linkedCountries);
//        Long res = mainPool.invoke(placeLinkTask);
//        System.out.println(res.longValue());
//
//    }

    public static void main(String[] args) {

    }
}
