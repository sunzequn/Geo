package com.sunzequn.geo.data.link;

import com.sunzequn.geo.data.climate.bean.Region;
import com.sunzequn.geo.data.climate.dao.RegionDao;
import com.sunzequn.geo.data.geonames.bean.Countryinfo;
import com.sunzequn.geo.data.geonames.bean.Geoname;
import com.sunzequn.geo.data.geonames.dao.CountryInfoDao;
import com.sunzequn.geo.data.geonames.dao.GeonameDao;

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
        int matchedNum = 1;
        for (LinkBean linkedCountry : linkedCountries) {
            Countryinfo countryinfo = countryInfoDao.getById(linkedCountry.getGeonameid());
            List<Region> regions = regionDao.getByParentId(linkedCountry.getClimateid());

            matchedNum += calculateFcode(countryinfo.getIso_alpha2(), "ADM1", regions);
            matchedNum += calculateFcode(countryinfo.getIso_alpha2(), "ADM2", regions);
            matchedNum += calculateFcode(countryinfo.getIso_alpha2(), "ADM3", regions);

            matchedNum += calculateFcode(countryinfo.getIso_alpha2(), "ADM1H", regions);
            matchedNum += calculateFcode(countryinfo.getIso_alpha2(), "ADM2H", regions);
            matchedNum += calculateFcode(countryinfo.getIso_alpha2(), "ADM3H", regions);
            matchedNum += calculateFcode(countryinfo.getIso_alpha2(), "ADM4", regions);
            matchedNum += calculateFcode(countryinfo.getIso_alpha2(), "ADM4H", regions);
            matchedNum += calculateFcode(countryinfo.getIso_alpha2(), "ADM5", regions);
            matchedNum += calculateFcode(countryinfo.getIso_alpha2(), "ADM5H", regions);

        }
        System.out.println(matchedNum);
    }

    private static int calculateFcode(String country, String fcode, List<Region> regions) {
        int matchedNum = 0;
        List<Geoname> geonames = geonameDao.countryChildrenByFcode(country, fcode);
        if (geonames != null && regions != null) {
            for (Region region : regions) {
                String name = region.getName();
                name = LinkUtils.climateNameClear(name);
                Geoname matchGeo = null;
                for (Geoname geo : geonames) {
                    String name2 = geo.getName();
                    String asciiname = geo.getAsciiname();
                    String alterName = geo.getAlternatenames();
                    if (LinkUtils.isNameEqual(name, name2) || LinkUtils.isNameEqual(name, asciiname) || LinkUtils.isAlternameEqual(name, alterName)) {
                        matchedNum++;
                        matchGeo = geo;
                        //1表示有完全匹配的
                        save(geo, region, 1);
                    }
                }
                if (matchGeo != null) {
                    geonames.remove(matchGeo);
                }
            }
        }
        return matchedNum;
    }

    private static void save(Geoname geoname, Region region, int state) {
        regionLinkDao.save(new LinkBean(geoname.getGeonameid(), region.getId()));
        regionDao.updateMatch(region.getId(), state);
    }
}
