package com.sunzequn.geo.data.climate.dao;

import com.sunzequn.geo.data.climate.bean.Place;
import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;

/**
 * Created by Sloriac on 16/1/7.
 */
public class PlaceDao extends BaseDao {

    private String table;
    private static final String DATABASE = "geocities";
    private Connection connection;

    public PlaceDao(String table) {
        connection = getConnection(DATABASE);
        this.table = table;
    }

    public int save(Place place) {
        String sql = "insert into " + table + " values (?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = {place.getId(), place.getName(), place.getUrl(), place.getParentid(),
                place.getIfvisited(), place.getClimate(), place.getTemperature(), place.getPrecipitation()};
        return execute(connection, sql, params);

    }

}
