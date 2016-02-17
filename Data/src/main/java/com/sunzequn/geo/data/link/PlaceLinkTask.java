package com.sunzequn.geo.data.link;

import com.sunzequn.geo.data.climate.bean.Place;
import com.sunzequn.geo.data.climate.bean.Region;
import com.sunzequn.geo.data.climate.dao.PlaceDao;
import com.sunzequn.geo.data.climate.dao.RegionDao;
import com.sunzequn.geo.data.geonames.bean.Countryinfo;
import com.sunzequn.geo.data.geonames.bean.Geoname;
import com.sunzequn.geo.data.geonames.dao.CountryInfoDao;
import com.sunzequn.geo.data.geonames.dao.GeonameDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * Created by sloriac on 16-2-17.
 */
public class PlaceLinkTask extends RecursiveTask<Long> {

    private static final long serialVersionUID = 1L;
    private int start;
    private int end;

    private static LinkDao countryLinkDao = new LinkDao("country_link");
    private static LinkDao placeLinkDao = new LinkDao("place_link");
    private static CountryInfoDao countryInfoDao = new CountryInfoDao();
    private static GeonameDao geonameDao = new GeonameDao();
    private static RegionDao regionDao = new RegionDao();
    private static PlaceDao placeFromCountryDao = new PlaceDao("climate_seed_place_from_country");
    private static PlaceDao placeDao = new PlaceDao("climate_seed_place");

    List<LinkBean> linkedCountries;

    public PlaceLinkTask(int start, int end, List<LinkBean> linkedCountries) {
        this.start = start;
        this.end = end;
        this.linkedCountries = linkedCountries;
    }

    @Override
    protected Long compute() {
        Long res = new Long(0);

        if (start == end) {
            res = match(start);
        } else {
            int mid = (start + end) / 2;
            PlaceLinkTask leftTask = new PlaceLinkTask(start, mid, linkedCountries);
            PlaceLinkTask rightTask = new PlaceLinkTask(mid + 1, end, linkedCountries);
            leftTask.fork();
            rightTask.fork();
            Long leftRes = leftTask.join();
            Long rightRes = rightTask.join();
            res = leftRes + rightRes;
        }
        return res;
    }

    private Long match(int index) {
        LinkBean linkedCountry = linkedCountries.get(index);
        int num = 0;
        List<Place> places = getClimatePlaces(linkedCountry.getClimateid());
        Countryinfo countryinfo = countryInfoDao.getById(linkedCountry.getGeonameid());

        num += calculateFcode(countryinfo.getIso_alpha2(), "ADM1", places);
        num += calculateFcode(countryinfo.getIso_alpha2(), "ADM2", places);
        num += calculateFcode(countryinfo.getIso_alpha2(), "ADM3", places);

        num += calculateFcode(countryinfo.getIso_alpha2(), "PPL", places);
        num += calculateFcode(countryinfo.getIso_alpha2(), "PPLA", places);
        num += calculateFcode(countryinfo.getIso_alpha2(), "PPLA2", places);
        num += calculateFcode(countryinfo.getIso_alpha2(), "PPLA3", places);
        num += calculateFcode(countryinfo.getIso_alpha2(), "PPLA4", places);
        num += calculateFcode(countryinfo.getIso_alpha2(), "PPLC", places);

//            num += calculateFcode(countryinfo.getIso_alpha2(), "ADM1H", places);
//            num += calculateFcode(countryinfo.getIso_alpha2(), "ADM2H", places);
//            num += calculateFcode(countryinfo.getIso_alpha2(), "ADM3H", places);
//            num += calculateFcode(countryinfo.getIso_alpha2(), "ADM4", places);
//            num += calculateFcode(countryinfo.getIso_alpha2(), "ADM4H", places);
//            num += calculateFcode(countryinfo.getIso_alpha2(), "ADM5", places);
//            num += calculateFcode(countryinfo.getIso_alpha2(), "ADM5H", places);
        System.out.println("matched num : " + num);
        return new Long(num);
    }

    private int calculateFcode(String country, String fcode, List<Place> places) {
        int matchedNum = 0;
        List<Geoname> geonames = geonameDao.countryChildrenByFcode(country, fcode);
        if (geonames != null && places != null) {
            for (Place place : places) {
                String name = place.getName();
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
//                        save(geo, place);
                    }
                }
                if (matchGeo != null) {
                    geonames.remove(matchGeo);
                }
            }
        }
        return matchedNum;
    }

    private void save(Geoname geoname, Place place) {
        placeLinkDao.save(new LinkBean(geoname.getGeonameid(), place.getId()));
    }

    private List<Place> getClimatePlaces(int countryId) {
        List<Place> places = new ArrayList<>();
        List<Place> placesFromCountry = placeFromCountryDao.getByParentId(countryId);
        if (placesFromCountry != null) {
            places.addAll(placesFromCountry);
        }
        List<Region> regions = regionDao.getByParentId(countryId);
        if (regions != null) {
            for (Region region : regions) {
                List<Place> placesFromRegion = placeDao.getByParentId(region.getId());
                if (placesFromRegion != null) {
                    places.addAll(placesFromRegion);
                }
            }
        }
        System.out.println("place num: " + places.size());
        return places;
    }

}
