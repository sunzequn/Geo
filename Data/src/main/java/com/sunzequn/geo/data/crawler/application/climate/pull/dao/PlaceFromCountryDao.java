package com.sunzequn.geo.data.crawler.application.climate.pull.dao;

import com.sunzequn.geo.data.crawler.application.climate.pull.bean.Place;
import com.sunzequn.geo.data.dao.BaseDao;

/**
 * Created by Sloriac on 16/1/7.
 */
public class PlaceFromCountryDao extends BaseDao {

    private static final String TABLE_NAME = "climate_seed_place_from_country";

    public PlaceFromCountryDao() {
        getConnection();
    }

    public int save(Place place) {
        String sql = "insert into " + TABLE_NAME + " values (?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = {place.getId(), place.getName(), place.getUrl(), place.getParentid(),
                place.getIfvisited(), place.getClimate(), place.getTemperature(), place.getPrecipitation()};
        return execute(sql, params);

    }

}
