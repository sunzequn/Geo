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

/**
 * Created by sloriac on 16-2-17.
 */
public class PlaceLink {

    private static LinkDao placeLinkDao = new LinkDao("place_link");
    private static GeonameDao geonameDao = new GeonameDao();
    private static RegionDao regionDao = new RegionDao();
    private static PlaceDao placeFromCountryDao = new PlaceDao("climate_seed_place_from_country");
    private static PlaceDao placeDao = new PlaceDao("climate_seed_place");

    public int calculateFcode(String country, String fcode, List<Place> places) {
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
                        save(geo, place);
                    }
                }
                if (matchGeo != null) {
                    geonames.remove(matchGeo);
                }
            }
        }
        return matchedNum;
    }

    public int calculateFclass(String country, String fclass, List<Place> places) {
        int matchedNum = 0;
        List<Geoname> geonames = geonameDao.countryChildrenByFclass(country, fclass);
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
                        save(geo, place);
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

    public List<Place> getClimatePlaces(int countryId) {
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
        return places;
    }

    public static void main(String[] args) {
        PlaceLink placeLink = new PlaceLink();
        List<Place> places = placeLink.getClimatePlaces(110);
        for (Place place : places) {
            System.out.println(place.getName());
        }
    }

}
