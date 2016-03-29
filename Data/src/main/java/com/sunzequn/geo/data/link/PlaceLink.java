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
    private static final int THREAD_NUM = 50;

    public PlaceLink() {

    }

    public static void main(String[] args) {
        init();
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(() -> {
                PlaceLink placeLink = new PlaceLink();
                while (true) {
                    LinkBean linkedRegion = getLinkedRegion();
                    if (linkedRegion == null) {
                        return;
                    }
                    System.out.print(Thread.currentThread().getName() + ": ");
                    placeLink.run(linkedRegion);
                }

            }, "thread" + i).start();
        }
    }

    private void run(LinkBean linkBean) {
        int geoId = linkBean.getGeonameid();
        int climateId = linkBean.getClimateid();
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

    private List<Place> calculate(List<Place> places, int geoId, String fcode) {
        int matchedNum = 0;
        List<Place> unMatchedPlaces = new ArrayList<>();
        List<Geoname> geonames = geonameDao.childrenByFcode(geoId, fcode);
        if (geonames != null && places != null) {
            //会出现多个place匹配到一个geonames上，暂时不管，后续处理
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

    /**
     * 会出现一个place匹配到多个geonames的情况，暂时不管，后续去重
     *
     * @param place
     * @param geonames
     * @return
     */
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
