package com.sunzequn.geo.data.geonames.dao;

import com.sunzequn.geo.data.dao.BaseDao;
import com.sunzequn.geo.data.geonames.bean.CountryInfo;

import java.util.List;

/**
 * Created by Sloriac on 16/2/16.
 */
public class CountryInfoDao extends BaseDao {
    private static final String DATABASE = "geonames";
    private static final String TABLE = "countryinfo";

    public CountryInfoDao() {
        getConnection(DATABASE);
    }

    public List<CountryInfo> getAll() {
        String sql = "select * from " + TABLE;
        return query(sql, null, CountryInfo.class);
    }

    public static void main(String[] args) {
        CountryInfoDao countryInfoDao = new CountryInfoDao();
        System.out.println(countryInfoDao.getAll());
    }
}
