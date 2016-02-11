package com.sunzequn.geo.data.climate.pull.dao;

import com.sunzequn.geo.data.climate.pull.bean.Place;
import com.sunzequn.geo.data.dao.BaseDao;

/**
 * Created by Sloriac on 16/1/7.
 */
public class PlaceDao extends BaseDao {

    private String table;

    public PlaceDao(String table) {
        getConnection();
        this.table = table;
    }

    public int save(Place place) {
        String sql = "insert into " + table + " values (?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = {place.getId(), place.getName(), place.getUrl(), place.getParentid(),
                place.getIfvisited(), place.getClimate(), place.getTemperature(), place.getPrecipitation()};
        return execute(sql, params);

    }

}
