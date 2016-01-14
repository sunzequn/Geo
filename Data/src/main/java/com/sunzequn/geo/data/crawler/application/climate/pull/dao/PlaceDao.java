package com.sunzequn.geo.data.crawler.application.climate.pull.dao;

import com.sunzequn.geo.data.crawler.application.climate.pull.bean.Place;
import com.sunzequn.geo.data.dao.BaseDao;

/**
 * Created by Sloriac on 16/1/7.
 */
public class PlaceDao extends BaseDao {

    public PlaceDao() {
        getConnection();
    }

    public int save(Place place, String table) {
        String sql = "insert into " + table + " values (?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = {place.getId(), place.getName(), place.getUrl(), place.getParentid(),
                place.getIfvisited(), place.getClimate(), place.getTemperature(), place.getPrecipitation()};
        return execute(sql, params);

    }

}
