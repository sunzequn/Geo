package com.sunzequn.geo.data.link;

import com.sunzequn.geo.data.climate.bean.Continent;
import com.sunzequn.geo.data.climate.bean.Country;
import com.sunzequn.geo.data.climate.dao.ContinentDao;
import com.sunzequn.geo.data.climate.dao.CountryDao;
import com.sunzequn.geo.data.geonames.bean.ContinentCodes;
import com.sunzequn.geo.data.geonames.bean.Countryinfo;
import com.sunzequn.geo.data.geonames.bean.Geoname;
import com.sunzequn.geo.data.geonames.dao.ContinentCodesDao;
import com.sunzequn.geo.data.geonames.dao.CountryInfoDao;
import com.sunzequn.geo.data.geonames.dao.GeonameDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sloriac on 16-2-14.
 */
public class CountryLink {

    public static void main(String[] args) {
//        calculate();
        LinkDao linkDao = new LinkDao("country_link");
        //Russian Federation
//        linkDao.save(new LinkBean(2017370, 136));
//        //East Timor
//        linkDao.save(new LinkBean(1966436, 170));
//        //Cyprus
//        linkDao.save(new LinkBean(146669, 106));

        //上面三个是因为所属大洲错误

    }

    private static void calculate() {

        LinkDao continentLinkDao = new LinkDao("continent_link");
        List<LinkBean> continentLinks = continentLinkDao.getAll();

        CountryDao countryDao = new CountryDao();
        ContinentDao continentDao = new ContinentDao();

        CountryInfoDao countryInfoDao = new CountryInfoDao();
        ContinentCodesDao continentCodesDao = new ContinentCodesDao();
        GeonameDao geonameDao = new GeonameDao();

        int matchedNum = 0;
        List<Country> allNoMatchedCountries = new ArrayList<>();

        for (LinkBean continentLink : continentLinks) {
            int geonameid = continentLink.getGeonameid();
            ContinentCodes continentCodes = continentCodesDao.getById(geonameid);
            int climateid = continentLink.getClimateid();
            Continent continent = continentDao.getById(climateid);

            List<Countryinfo> countryInfos = countryInfoDao.getByContinent(continentCodes.getCode());
            List<Geoname> geoCountries = new ArrayList<>();
            for (Countryinfo countryInfo : countryInfos) {
                Geoname geoname = geonameDao.getById(countryInfo.getGeonameId());
                if (geoname != null)
                    geoCountries.add(geoname);
            }
            System.out.println(continentCodes.getName() + "的国家数量： " + geoCountries.size());

            List<Country> countries = countryDao.getByParentId(continent.getId());
            System.out.println(continentCodes.getName() + "的国家数量： " + countries.size());

            List<Country> noMatchedCountries = new ArrayList<>();

            for (Country country : countries) {
                String name1 = country.getName();
                Geoname matchedGeoCountry = null;
                for (Geoname geoCountry : geoCountries) {
                    double similarity = LinkUtils.isNameEqual(geoCountry.getName(), name1);
                    double alterSimilarity = LinkUtils.isAlternameEqual(name1, geoCountry.getAlternatenames());
                    if (similarity < alterSimilarity) {
                        similarity = alterSimilarity;
                    }
                    if (similarity > 0) {
                        System.out.println("matched: " + geoCountry.getName() + " , " + name1);
                        save(geoCountry, country, similarity);
                    }
                }
                if (matchedGeoCountry == null) {
                    noMatchedCountries.add(country);
                } else {
                    geoCountries.remove(matchedGeoCountry);
                }
            }
            allNoMatchedCountries.addAll(noMatchedCountries);
            System.out.println(noMatchedCountries);
        }
        System.out.println(allNoMatchedCountries.size());
        for (Country c : allNoMatchedCountries) {
            System.out.println(c);
        }
    }

    private static void save(Geoname geoname, Country country, double state) {

        LinkDao linkDao = new LinkDao("country_link");
        LinkBean linkBean = new LinkBean(geoname.getGeonameid(), country.getId(), state);
        linkDao.save(linkBean);

        /*
        剩下5个，气候数据的层级关系有误,所属大洲有错误
Country{id=251, name='Sahrawi Arab Democratic Republic', url='/country/251/', parentid=1, ifvisited=0}
Country{id=252, name='Saint Helena, Ascension and Tristan da Cunha', url='/country/252/', parentid=1, ifvisited=0}
Country{id=106, name='Cyprus', url='/country/106/', parentid=4, ifvisited=0}
Country{id=136, name='Russian Federation', url='/country/136/', parentid=4, ifvisited=0}
Country{id=170, name='East Timor', url='/country/170/', parentid=4, ifvisited=0}
         */
    }


}
