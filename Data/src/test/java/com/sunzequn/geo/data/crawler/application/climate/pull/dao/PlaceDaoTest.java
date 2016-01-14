package com.sunzequn.geo.data.crawler.application.climate.pull.dao;

import com.sunzequn.geo.data.crawler.application.climate.pull.bean.Place;
import org.junit.Test;

/**
 * Created by Sloriac on 16/1/8.
 */
public class PlaceDaoTest {

    private PlaceDao placeDao = new PlaceDao();

    @Test
    public void saveTest() {
        Place place = new Place(1, "nan", "url", 1, 0, "climate", 1.0, 1.0);
        placeDao.save(place);
    }
}
