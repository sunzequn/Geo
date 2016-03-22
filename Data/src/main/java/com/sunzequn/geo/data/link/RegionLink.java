package com.sunzequn.geo.data.link;

import com.sunzequn.geo.data.climate.bean.Region;
import com.sunzequn.geo.data.climate.dao.RegionDao;
import com.sunzequn.geo.data.geonames.bean.Countryinfo;
import com.sunzequn.geo.data.geonames.bean.Geoname;
import com.sunzequn.geo.data.geonames.dao.CountryInfoDao;
import com.sunzequn.geo.data.geonames.dao.GeonameDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sloriac on 16-2-17.
 */
public class RegionLink {

    private static LinkDao countryLinkDao = new LinkDao("country_link");
    private static LinkDao regionLinkDao = new LinkDao("region_link");
    private static CountryInfoDao countryInfoDao = new CountryInfoDao();
    private static GeonameDao geonameDao = new GeonameDao();
    private static RegionDao regionDao = new RegionDao();

    public static void main(String[] args) {
        calculate();
    }

    private static void calculate() {

        List<LinkBean> linkedCountries = countryLinkDao.getAll();
        for (LinkBean linkedCountry : linkedCountries) {
            Countryinfo countryinfo = countryInfoDao.getById(linkedCountry.getGeonameid());
            List<Region> regions = regionDao.getByParentId(linkedCountry.getClimateid());
            if (regions == null) {
                System.out.println(linkedCountry.getClimateid() + " region null");
                continue;
            }
            regions = calculateFcode(countryinfo.getIso_alpha2(), "ADM1", regions);
//            regions = calculateFcode(countryinfo.getIso_alpha2(), "ADM1H", regions);

            System.out.println(countryinfo.getIso_alpha2() + " 未匹配的：" + regions);
        }
    }

    private static List<Region> calculateFcode(String country, String fcode, List<Region> regions) {
        int matchedNum = 0;
        List<Region> unMatchedRegions = new ArrayList<>();
        List<Geoname> geonames = geonameDao.countryChildrenByFcode(country, fcode);
        if (geonames != null && regions != null) {
            for (Region region : regions) {
                if (match(region, geonames)) {
                    matchedNum++;
                } else {
                    unMatchedRegions.add(region);
                }
            }
            System.out.println(country + ": region num: " + regions.size() + "; matched num: " + matchedNum);
            return unMatchedRegions;
        } else {
            return regions;
        }


    }

    private static boolean match(Region region, List<Geoname> geonames) {

        String name = region.getName();
        boolean hasMatch = false;

        for (Geoname geo : geonames) {
            String geoName = geo.getName();
            String asciiname = geo.getAsciiname();
            String alterName = geo.getAlternatenames();
            double similarity = LinkUtils.isEqual(geoName, asciiname, alterName, name);
            if (similarity > 0.0) {
                save(geo, region, similarity);
                hasMatch = true;
            }
        }
        return hasMatch;
    }

    private static void save(Geoname geoname, Region region, double confidence) {
        regionLinkDao.save(new LinkBean(geoname.getGeonameid(), region.getId(), confidence));
        regionDao.updateMatch(region.getId(), 1);
    }
}
