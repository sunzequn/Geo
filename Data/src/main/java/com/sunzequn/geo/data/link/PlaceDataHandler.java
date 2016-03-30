package com.sunzequn.geo.data.link;

import com.sunzequn.geo.data.algorithm.location.distance.LongLatCalculator;
import com.sunzequn.geo.data.climate.bean.Place;
import com.sunzequn.geo.data.climate.dao.PlaceDao;
import com.sunzequn.geo.data.climate.dao.RegionDao;
import com.sunzequn.geo.data.geonames.bean.Geoname;
import com.sunzequn.geo.data.geonames.dao.GeonameDao;

import java.util.*;

/**
 * Created by sloriac on 16-3-22.
 * <p>
 * 去掉link表里面出现一对多的现象
 * 对于一个climate对应多个geonames,取置信度最高的，如果置信度最高的有多个，判断一下这些地点的距离，距离小于一个阈值的话，则全部保留这些匹配，否则，表示出错
 * 经过上述处理，可能会出现一个geonames对应多个climate的问题，如果这些climate的气候类型是一样的，则不处理，否则出错
 */
public class PlaceDataHandler {

    private static final double DIS = 100 * 1000.0;

    private static LinkDao linkDao = new LinkDao("place_link");
    private static PlaceDao placeDao = new PlaceDao("climate_seed_place");
    private static GeonameDao geonameDao = new GeonameDao();
    private static LongLatCalculator longLatCalculator = new LongLatCalculator();

    public static void main(String[] args) {
//        handleClimateId();
        handleGeonameId();
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
                double maxConfidence = linksWithSameGeonameIds.get(0).getConfidence();
                int index = 0;
                for (int i = 0; i < linksWithSameGeonameIds.size(); i++) {
                    LinkBean linkBean = linksWithSameGeonameIds.get(i);
                    if (linkBean.getConfidence() == maxConfidence) {
                        index = i;
                    } else {
                        break;
                    }
                }
                Set<String> climateType = new HashSet<>();
                for (int i = 0; i <= index; i++) {
                    Place place = placeDao.getById(linksWithSameGeonameIds.get(i).getClimateid());
                    climateType.add(place.getClimate());
                }
                if (climateType.size() > 1) {
                    System.out.println("不正常的数据 ： ");
                    for (int i = 0; i <= index; i++) {
                        System.out.println(linksWithSameGeonameIds.get(i));
                    }
                }
                //index就是最大置信度的最大下标，所以后面的数据都要删除
//                for (int i = 1; i < linksWithSameGeonameIds.size(); i++) {
//                    LinkBean linkBean = linksWithSameGeonameIds.get(i);
//                    linkDao.deleteLine(linkBean.getGeonameid(), linkBean.getClimateid());
//                    System.out.println(linkBean.getConfidence());
//                }
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
                //最大置信度
                double maxConfidence = linksWithSameClimateIds.get(0).getConfidence();
                System.out.println("最大置信度：" + maxConfidence);
                int index = 0;
                for (int i = 0; i < linksWithSameClimateIds.size(); i++) {
                    LinkBean linkBean = linksWithSameClimateIds.get(i);
                    if (linkBean.getConfidence() == maxConfidence) {
                        index = i;
                    } else {
                        break;
                    }
                }
                //index就是最大置信度的最大下标
                System.out.println("最大下标：" + index);

                //index及之前的数据，要判断geonames的距离
                List<Geoname> geonames = new ArrayList<>();
                for (int i = 0; i <= index; i++) {
                    LinkBean linkBean = linksWithSameClimateIds.get(i);
                    Geoname geoname = geonameDao.getById(linkBean.getGeonameid());
                    if (geoname == null) {
                        System.out.println("Geonames出错");
                    } else {
                        geonames.add(geoname);
                    }
                }
                if (!handleDis(geonames)) {
                    System.out.println("Dis 出错");
                    for (Geoname g : geonames) {
                        System.out.println(g);
                    }
                }
                //index之后的是数据都要删除
                for (int i = index + 1; i < linksWithSameClimateIds.size(); i++) {
                    LinkBean linkBean = linksWithSameClimateIds.get(i);
//                    linkDao.deleteLine(linkBean.getGeonameid(), linkBean.getClimateid());
                }

            }
        }
    }

    private static boolean handleDis(List<Geoname> geonames) {
        for (int i = 0; i < geonames.size() - 1; i++) {
            Geoname g1 = geonames.get(i);
            for (int j = i + 1; j < geonames.size(); j++) {
                Geoname g2 = geonames.get(j);
                if (!longLatCalculator.isNear(g1.getLatitude(), g1.getLongitude(), g2.getLatitude(), g2.getLongitude(), DIS)) {
                    return false;
                }
            }
        }
        return true;
    }

}
