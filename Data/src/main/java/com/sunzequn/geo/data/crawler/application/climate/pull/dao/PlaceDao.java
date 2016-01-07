package com.sunzequn.geo.data.crawler.application.climate.pull.dao;

import com.sunzequn.geo.data.crawler.application.climate.pull.bean.Place;
import com.sunzequn.geo.data.dao.BaseDao;

/**
 * Created by Sloriac on 16/1/7.
 */
public class PlaceDao extends BaseDao {

    private static final String TABLE_NAME = "climate_seed_place";

    public int save(Place place) {
        return 0;
    }

}
