package com.sunzequn.geo.data.link;

import com.sunzequn.geo.data.climate.bean.Place;
import com.sunzequn.geo.data.climate.dao.PlaceDao;
import com.sunzequn.geo.data.geonames.bean.Geoname;
import com.sunzequn.geo.data.geonames.dao.GeonameDao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sloriac on 16-2-17.
 */
public class PlaceLink {

    private static GeonameDao geonameDao = new GeonameDao();
    private static PlaceDao placeDao = new PlaceDao("climate_seed_place");
    private static LinkDao placeLinkDao = new LinkDao("place_link");
    private static LinkDao regionLinkDao = new LinkDao("region_link");
    private static LinkedList<LinkBean> linkedRegions = new LinkedList<>();

    public PlaceLink() {

    }

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        List<LinkBean> linkRegions = regionLinkDao.getAbove1();
        for (LinkBean linkRegion : linkRegions) {
            int geoId = linkRegion.getGeonameid();
            int climateId = linkRegion.getClimateid();
            List<Place> places = getClimatePlaces(climateId);
            System.out.println(climateId + " =======================");
            places = calculate(places, geoId, "ADM2");
            places = calculate(places, geoId, "ADM3");
            places = calculate(places, geoId, "ADM4");
            places = calculate(places, geoId, "ADM5");
            places = calculate(places, geoId, "PPL");
            places = calculate(places, geoId, "PPLA");
            places = calculate(places, geoId, "PPLA2");
            places = calculate(places, geoId, "PPLA3");
            System.out.println(climateId + " -----------------------");
        }
    }

    private static List<Place> calculate(List<Place> places, int geoId, String fcode) {
        int matchedNum = 0;
        List<Place> unMatchedPlaces = new ArrayList<>();
        List<Geoname> geonames = geonameDao.admChildrenByFcode(geoId, fcode);
        if (geonames != null && places != null) {
            for (Place place : places) {
                if (match(place, geonames)) {
                    matchedNum++;
                } else {
                    unMatchedPlaces.add(place);
                }
            }
            System.out.println(fcode + " : place num: " + places.size() + "; matched num: " + matchedNum);
            return unMatchedPlaces;
        } else {
            return places;
        }
    }

    private static boolean match(Place place, List<Geoname> geonames) {

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

    private static void save(Geoname geoname, Place place, double similarity) {
        placeLinkDao.save(new LinkBean(geoname.getGeonameid(), place.getId(), similarity));
        //update
    }

    public static List<Place> getClimatePlaces(int parentId) {
        return placeDao.getByParentId(parentId);
    }

    private static synchronized LinkBean getLinkedRegion() {
        if (linkedRegions.size() > 0) {
            return linkedRegions.pop();
        }
        return null;
    }

    private static synchronized void init() {
        linkedRegions.addAll(regionLinkDao.getAbove1());
    }

}
