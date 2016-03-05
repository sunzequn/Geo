package com.sunzequn.geo.data.climate.dao;

import com.sunzequn.geo.data.climate.bean.PlaceDetail;
import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;

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

    public List<PlaceDetail> getAllUrls() {
        String sql = "select url from " + table;
        return query(connection, sql, null, PlaceDetail.class);
    }

    public static void main(String[] args) {
        PlaceDetailDao placeDetailDao = new PlaceDetailDao();
        System.out.println(placeDetailDao.getAllUrls().size());
    }
}
