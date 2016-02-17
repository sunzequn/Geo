package com.sunzequn.geo.data.geonames.dao;

import com.sunzequn.geo.data.dao.BaseDao;
import com.sunzequn.geo.data.geonames.bean.Countryinfo;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Sloriac on 16/2/16.
 */
public class CountryInfoDao extends BaseDao {
    private static final String DATABASE = "geonames";
    private static final String TABLE = "countryinfo";
    private Connection connection;

    public CountryInfoDao() {
        connection = getConnection(DATABASE);
    }

    public List<Countryinfo> getAll() {
        String sql = "select * from " + TABLE;
        return query(connection, sql, null, Countryinfo.class);
    }

    public List<Countryinfo> getByContinent(String code) {
        String sql = "select * from " + TABLE + " where continent = ?";
        Object[] params = {code};
        return query(connection, sql, params, Countryinfo.class);
    }

    public static void main(String[] args) {
        CountryInfoDao countryInfoDao = new CountryInfoDao();
        System.out.println(countryInfoDao.getAll());
    }
}
