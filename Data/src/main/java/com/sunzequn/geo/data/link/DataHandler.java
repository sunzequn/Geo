package com.sunzequn.geo.data.link;

import java.util.*;

/**
 * Created by sloriac on 16-3-22.
 * <p>
 * 去掉link表里面出现一堆多的现象
 */
public class DataHandler {

    private static LinkDao linkDao = new LinkDao("place_link");

    public static void main(String[] args) {
        handleGeonameId();
        handleClimateId();
    }

    private static void handleGeonameId() {
        Set<Integer> geonameids = new HashSet<>();
        List<LinkBean> allLinkedRegions = linkDao.getAll();
        System.out.println(allLinkedRegions.size());
        for (LinkBean linkBean : allLinkedRegions) {
            geonameids.add(linkBean.getGeonameid());
        }
        for (int geonameid : geonameids) {
            List<LinkBean> linksWithSameGeonameIds = linkDao.getByGeonameid(geonameid);
            if (linksWithSameGeonameIds.size() > 1) {
                Collections.sort(linksWithSameGeonameIds);
                for (int i = 1; i < linksWithSameGeonameIds.size(); i++) {
                    LinkBean linkBean = linksWithSameGeonameIds.get(i);
                    linkDao.deleteLine(linkBean.getGeonameid(), linkBean.getClimateid());
                    System.out.println(linkBean.getConfidence());
                }
            }
        }
    }

    private static void handleClimateId() {
        Set<Integer> climateids = new HashSet<>();
        List<LinkBean> allLinkedRegions = linkDao.getAll();
        System.out.println(allLinkedRegions.size());
        for (LinkBean linkBean : allLinkedRegions) {
            climateids.add(linkBean.getClimateid());
        }
        for (int climateid : climateids) {
            List<LinkBean> linksWithSameClimateIds = linkDao.getByClimateid(climateid);
            if (linksWithSameClimateIds.size() > 1) {
                Collections.sort(linksWithSameClimateIds);
                for (int i = 1; i < linksWithSameClimateIds.size(); i++) {
                    LinkBean linkBean = linksWithSameClimateIds.get(i);
                    linkDao.deleteLine(linkBean.getGeonameid(), linkBean.getClimateid());
                }
            }
        }
    }

}
