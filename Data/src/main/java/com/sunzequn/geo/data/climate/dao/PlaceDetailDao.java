package com.sunzequn.geo.data.climate.dao;

import com.sunzequn.geo.data.climate.bean.PlaceDetail;
import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;

/**
 * Created by Sloriac on 16/3/3.
 */
public class PlaceDetailDao extends BaseDao {

    private static final String DATABASE = "geocities";
    private static final String table = "climate_place_detail";
    private Connection connection;

    public PlaceDetailDao() {
        this.connection = getConnection(DATABASE);
    }

    public int save(PlaceDetail placeDetail) {
        String sql = "insert into " + table + " values (?, ?)";
        Object[] params = {placeDetail.getUrl(), placeDetail.getDetail()};
        return execute(connection, sql, params);
    }
}
