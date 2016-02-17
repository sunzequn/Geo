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

    public static void main(String[] args) {
        calculate();
    }

    private static void calculate() {

        LinkDao linkDao = new LinkDao("country_link");
        List<LinkBean> linkedCountries = linkDao.getAll();

        CountryInfoDao countryInfoDao = new CountryInfoDao();
        GeonameDao geonameDao = new GeonameDao();

        RegionDao regionDao = new RegionDao();

        int matchedNum = 1;

        for (LinkBean linkedCountry : linkedCountries) {
            Countryinfo countryinfo = countryInfoDao.getById(linkedCountry.getGeonameid());

            String adm = "ADM1";

            List<Geoname> geonamesADM = geonameDao.countryChildrenByFcode(countryinfo.getIso_alpha2(), adm);
            List<Region> regions = regionDao.getByParentId(linkedCountry.getClimateid());

            if (geonamesADM != null && regions != null) {

                for (Region region : regions) {

                    String name = region.getName();
                    name = LinkUtils.clear(name);
                    Geoname matchGeo = null;

                    for (Geoname geo : geonamesADM) {
                        String name2 = geo.getName();
                        String asciiname = geo.getAsciiname();
                        String alterName = geo.getAlternatenames();

                        if (LinkUtils.isNameEqual(name, name2) || LinkUtils.isNameEqual(name, asciiname) || LinkUtils.isAlternameEqual(name, alterName)) {
                            matchedNum++;
                            matchGeo = geo;
                            break;
                        }
                    }

                    if (matchGeo != null) {
                        geonamesADM.remove(matchGeo);
                    } else {
                        System.out.println(region);
                    }
                }

            }
        }
        System.out.println(matchedNum);
    }
}
