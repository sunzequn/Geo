package com.sunzequn.geo.data.link;

import com.sunzequn.geo.data.climate.bean.Place;
import com.sunzequn.geo.data.climate.dao.PlaceDao;
import com.sunzequn.geo.data.geonames.bean.Geoname;
import com.sunzequn.geo.data.geonames.dao.GeonameDao;

import java.util.*;

/**
 * Created by sloriac on 16-3-28.
 * <p>
 * 匹配那些直接属于国家的地点，没有地区作为一个范围限制，可能误差较大
 * 代码思路：
 * 1、默认已经匹配的（置信度大于1）的geonames先去掉。
 * 2、还是按照之前的思路，先匹配，然后去重
 */
public class PlaceFromCountryLink {

    private static GeonameDao geonameDao = new GeonameDao();
    private static PlaceDao placeDao = new PlaceDao("climate_seed_place_from_country");
    private static LinkDao placeLinkDao = new LinkDao("place_link");
    private static LinkDao placeFromCountryLinkDao = new LinkDao("place_from_country_link");
    private static LinkDao countryLinkDao = new LinkDao("country_link");
    private static LinkedList<Place> placesFromCountry = new LinkedList<>();
    private static Set<Integer> hasMatchedGeonamesIds = new HashSet<>();
    //气候数据和geonames国家数据间的对应关系，气候国家id在前(key)，geonames国家id在后(value)
    private static Map<Integer, Integer> countryMap = new HashMap<>();


    public static void main(String[] args) {
        initHasMatched();
        initPlace();
        initCountryMap();
        while (true) {
            Place place = getPlace();
            if (place == null) {
                return;
            }
            run(place);
        }
    }

    private static void run(Place place) {
        place = calculate(place, "ADM1");
        place = calculate(place, "ADM2");
        place = calculate(place, "ADM3");
        place = calculate(place, "ADM4");
        place = calculate(place, "ADM5");
        place = calculate(place, "PPL");
        place = calculate(place, "PPLA");
        place = calculate(place, "PPLA2");
        place = calculate(place, "PPLA3");

    }

    private static Place calculate(Place place, String fcode) {
        if (place == null) {
            return null;
        }
        int countryId = place.getParentid();
        Integer geoNamesCountryId = countryMap.get(countryId);
        if (geoNamesCountryId == null) {
            return null;
        }
        List<Geoname> geonames = geonameDao.countryChildrenByFcode(geoNamesCountryId, fcode);
        if (geonames != null) {
            //会出现多个place匹配到一个geonames上，暂时不管，后续处理
            if (match(place, geonames)) {
                return null;
            } else {
                return place;
            }
        }
        return null;
    }


    /**
     * 会出现一个place匹配到多个geonames的情况，暂时不管，后续去重
     *
     * @param place
     * @param geonames
     * @return
     */
    private static boolean match(Place place, List<Geoname> geonames) {

        String name = place.getName();
        boolean hasMatch = false;
        for (Geoname geo : geonames) {
            if (hasMatchedGeonamesIds.contains(geo.getGeonameid())) {
                continue;
            }
            String geoName = geo.getName();
            String asciiname = geo.getAsciiname();
            String alterName = geo.getAlternatenames();
            double similarity = LinkUtils.isEqual(geoName, asciiname, alterName, name);
            if (similarity > 0.0) {
                save(geo, place, similarity);
                hasMatch = true;
            }
        }
        return hasMatch;
    }

    private static void save(Geoname geoname, Place place, double similarity) {
        LinkBean linkBean = new LinkBean(geoname.getGeonameid(), place.getId(), similarity);
        System.out.println(linkBean);
        placeFromCountryLinkDao.save(linkBean);
        //update
    }

    private static synchronized Place getPlace() {
        if (placesFromCountry.size() > 0) {
            return placesFromCountry.pop();
        }
        return null;
    }

    private static void initHasMatched() {
        List<LinkBean> linkBeans = placeLinkDao.getAbove1();
        for (LinkBean linkBean : linkBeans) {
            hasMatchedGeonamesIds.add(linkBean.getGeonameid());
        }
        System.out.println("has matched geonames: " + hasMatchedGeonamesIds.size());
    }

    private static void initPlace() {
        placesFromCountry.addAll(placeDao.getAll());
        System.out.println("place size: " + placesFromCountry.size());
    }

    private static void initCountryMap() {
        List<LinkBean> linkedCountry = countryLinkDao.getAbove1();
        for (LinkBean linkBean : linkedCountry) {
            countryMap.put(linkBean.getClimateid(), linkBean.getGeonameid());
        }
        System.out.println("country map: " + countryMap.size());
    }


}
