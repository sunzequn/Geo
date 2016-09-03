package com.sunzequn.geo.data.china_geoname_link;

import com.sunzequn.geo.data.china.geo.ChinaCity;
import com.sunzequn.geo.data.china.geo.ChinaCityDao;
import com.sunzequn.geo.data.geonames.bean.Geoname;
import com.sunzequn.geo.data.geonames.dao.GeonameDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunzequn on 2016/6/28.
 * <p>
 * 关于置信度，1表示name匹配，等级匹配；
 * 0.9表示shortname匹配，等级匹配
 * 0.8表示等级不匹配
 */
public class LinkHandler {

    private static ChinaGeonamesLinkDao linkDao = new ChinaGeonamesLinkDao();
    private static ChinaCityDao chinaCityDao = new ChinaCityDao();
    private static GeonameDao geonameDao = new GeonameDao();

    public static void main(String[] args) {
//        handleADM1();
//        handleADM2();
//        handleADM3();
        handleTwXgAm();
    }

    private static void handle(List<ChinaCity> chinaCities, List<Geoname> geonames, String level) {
        for (ChinaCity chinaCity : chinaCities) {
            for (Geoname geoname : geonames) {
                double confidence = isEqual(geoname, chinaCity);
                if (confidence > 0) {
                    System.out.println(level + " : " + chinaCity.getName() + " = " + geoname.getName() + " : " + geoname.getAlternatenames());
                    linkDao.save(new ChinaGeonamesLink(chinaCity.getId(), geoname.getGeonameid(), level, confidence));
                    break;
                }
            }
        }
    }

    private static void handleADM1() {
        List<Geoname> geonames = geonameDao.countryChildrenByFcode("CN", "ADM1");
        List<ChinaCity> chinaCities = chinaCityDao.getByLevel(1);
        handle(chinaCities, geonames, "ADM1");
    }

    private static void handleADM2() {
        List<ChinaGeonamesLink> links = linkDao.getByLevel("ADM1");
        for (ChinaGeonamesLink link : links) {
            List<ChinaCity> chinaCities = chinaCityDao.getChildren(link.getCityid());
            ChinaCity zhixiashi = null;
            //重庆比较特殊
            if (chinaCities.size() == 1 || link.getCityid() == 500000) {
                zhixiashi = chinaCities.get(0);
                System.out.println("-------");
                System.out.println("直辖市： " + zhixiashi.getName());
                chinaCities = chinaCityDao.getChildren(chinaCities.get(0).getId());
            } else {
                System.out.println("-------");
                System.out.println("普通： " + chinaCityDao.getById(link.getCityid()));
            }
            List<Geoname> geonames = geonameDao.getADM2(link.getGeonameid());
            System.out.println(geonames);
            //geonames没有二级直辖市,特殊处理一下geonames的上海
            if (geonames == null || (zhixiashi != null && zhixiashi.getName().equals("上海市"))) {
                if (zhixiashi != null) {
                    linkDao.save(new ChinaGeonamesLink(zhixiashi.getId(), link.getGeonameid(), "ADM2", 0.8));
                } else {
                    System.out.println("error!!!");
                    System.out.println(chinaCities);
                }
                geonames = geonameDao.getADM3ByADM1(link.getGeonameid());
            }

//            System.out.println(chinaCities);
//            System.out.println(geonames);
            if (zhixiashi == null) {
                handle(chinaCities, geonames, "ADM2");
            } else {
                handle(chinaCities, geonames, "ADM3");
            }
        }
    }

    private static void handleADM3() {
        List<ChinaGeonamesLink> links = linkDao.getByLevel("ADM1");
        for (ChinaGeonamesLink link : links) {
            if (link.getCityid() == 110000 || link.getCityid() == 120000 || link.getCityid() == 500000 || link.getCityid() == 310000)
                continue;
            //ADM2
            List<ChinaCity> chinaCities = chinaCityDao.getChildren(link.getCityid());
            List<ChinaCity> sanjicities = new ArrayList<>();
            for (ChinaCity chinaCity : chinaCities) {
                List<ChinaCity> adm3s = chinaCityDao.getChildren(chinaCity.getId());
                if (adm3s != null)
                    sanjicities.addAll(adm3s);
            }
            List<Geoname> geonames = geonameDao.getADM3ByADM1(link.getGeonameid());
//            System.out.println(geonames);
            handle(sanjicities, geonames, "ADM3");
        }
    }

    private static double isEqual(Geoname geoname, ChinaCity chinaCity) {
        String[] altnames = geoname.getAlternatenames().split(",");
        String cityName = chinaCity.getName().trim();
        String shortName = chinaCity.getShortname().trim();
        double res = 0;
        for (String altname : altnames) {
            altname = altname.trim();
            if (altname.equals(cityName)) {
                res = Math.max(res, 1.0);
            } else if (altname.equals(shortName)) {
                res = Math.max(res, 0.9);
            }
        }
        return res;
    }

    /**
     * 处理香港台湾澳门
     */
    private static void handleTwXgAm() {
        ChinaGeonamesLink taiwanlink = new ChinaGeonamesLink(710000, 1668284, "ADM1", 2);
        ChinaGeonamesLink xiangganglink = new ChinaGeonamesLink(810000, 1819730, "ADM1", 2);
        ChinaGeonamesLink aomenlink = new ChinaGeonamesLink(820000, 1821275, "ADM1", 2);
        ChinaGeonamesLinkDao linkDao = new ChinaGeonamesLinkDao();
        linkDao.save(taiwanlink);
        linkDao.save(xiangganglink);
        linkDao.save(aomenlink);
        handleTwXgAmADM2(taiwanlink);
        handleTwXgAmADM2(xiangganglink);
        handleTwXgAmADM2(aomenlink);


    }

    private static void handleTwXgAmADM2(ChinaGeonamesLink link) {
        List<ChinaCity> chinaCities2 = chinaCityDao.getChildren(link.getCityid());
        List<Geoname> geonames = geonameDao.getADM2ByCountry(link.getGeonameid());
        System.out.println(link);
        System.out.println(geonames);
        if (geonames != null) {
            handle(chinaCities2, geonames, "ADM2");
        }

    }

}
