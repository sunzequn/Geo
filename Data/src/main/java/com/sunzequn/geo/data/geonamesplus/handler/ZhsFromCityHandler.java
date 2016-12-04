package com.sunzequn.geo.data.geonamesplus.handler;

import com.sunzequn.geo.data.china.geo.ChinaCity;
import com.sunzequn.geo.data.china.geo.ChinaCityDao;
import com.sunzequn.geo.data.china_geoname_link.ChinaGeonamesLink;
import com.sunzequn.geo.data.china_geoname_link.ChinaGeonamesLinkDao;
import com.sunzequn.geo.data.utils.ReadUtils;
import com.sunzequn.geo.data.utils.WriteUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sloriac on 16-12-4.
 *
 * 根据geonames链接到china city补充中文名
 */
public class ZhsFromCityHandler {

    public static void main(String[] args) {
//        zhsCity();
        zhs2All();
    }

    private static void zhs2All() {
        Set<String> res = new HashSet<>();
        ReadUtils reader1 = new ReadUtils(GeoNamesConf.GEONAMES_ZH);
        List<String> lines1 = reader1.readByLine();
        System.out.println(lines1.size());
        for (String s : lines1) {
            res.add(s.trim());
        }

        ReadUtils reader2 = new ReadUtils(GeoNamesConf.GEONAMES_ZH_CITY);
        List<String> lines2 = reader2.readByLine();
        System.out.println(lines2.size());
        for (String s : lines2) {
            res.add(s.trim());
        }
        System.out.println(res.size());

        WriteUtils writeUtils = new WriteUtils(GeoNamesConf.GEONAMES_ZH_ALL, false);
        for (String re : res) {
            writeUtils.write(re);
        }
        writeUtils.close();

    }

    private static void zhsCity() {
        WriteUtils writeUtils = new WriteUtils(GeoNamesConf.GEONAMES_ZH_CITY, false);
        ChinaGeonamesLinkDao chinaGeonamesLinkDao = new ChinaGeonamesLinkDao();
        ChinaCityDao chinaCityDao = new ChinaCityDao();
        List<ChinaGeonamesLink> links = chinaGeonamesLinkDao.getAll();
        for (ChinaGeonamesLink link : links) {
            ChinaCity chinaCity = chinaCityDao.getById(link.getCityid());
            String line = link.getGeonameid() + GeoNamesConf.SPLIT + chinaCity.getName();
            writeUtils.write(line);
            line = link.getGeonameid() + GeoNamesConf.SPLIT + chinaCity.getShortname();
            writeUtils.write(line);
        }
        writeUtils.close();
    }
}
