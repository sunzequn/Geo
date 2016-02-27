package com.sunzequn.geo.data.link;

import com.sunzequn.geo.data.climate.bean.Place;
import com.sunzequn.geo.data.climate.dao.PlaceDao;
import com.sunzequn.geo.data.geonames.bean.Geoname;
import com.sunzequn.geo.data.geonames.dao.GeonameDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sloriac on 16-2-17.
 */
public class PlaceLink {

    private static GeonameDao geonameDao = new GeonameDao();
    private static PlaceDao placeDao = new PlaceDao("climate_seed_place");
    private static LinkDao placeLinkDao = new LinkDao("place_link");

    public PlaceLink() {

    }

    public List<Place> calculateFcode(String country, String fcode, List<Place> places) {
        List<Geoname> geonames = geonameDao.countryChildrenByFcode(country, fcode);
        return calculate(geonames, places);
    }

    public List<Place> calculateFclass(String country, String fclass, List<Place> places) {

        List<Geoname> geonames = geonameDao.countryChildrenByFclass(country, fclass);
        return calculate(geonames, places);
    }

    private List<Place> calculate(List<Geoname> geonames, List<Place> places) {
        int matchedNum = 0;
        List<Place> unMatchedPlaces = new ArrayList<>();
        if (geonames != null && places != null) {
            for (Place place : places) {
                if (match(place, geonames)) {
                    matchedNum++;
                } else {
                    unMatchedPlaces.add(place);
                }
            }
            System.out.println(": place num: " + places.size() + "; matched num: " + matchedNum);
            return unMatchedPlaces;

        } else {
            return places;
        }
    }

    private boolean match(Place place, List<Geoname> geonames) {

        String name = place.getName();
        boolean hasMatch = false;
        for (Geoname geo : geonames) {
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

    private void save(Geoname geoname, Place place, double similarity) {
        placeLinkDao.save(new LinkBean(geoname.getGeonameid(), place.getId(), similarity));
        //update
    }

    public List<Place> getClimatePlaces(int parentId) {
        return placeDao.getByParentId(parentId);
    }
}
