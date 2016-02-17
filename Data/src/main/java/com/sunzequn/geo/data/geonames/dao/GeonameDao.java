package com.sunzequn.geo.data.geonames.dao;

import com.sunzequn.geo.data.dao.BaseDao;
import com.sunzequn.geo.data.geonames.bean.Geoname;
import com.sunzequn.geo.data.utils.ListUtils;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sloriac on 16-2-14.
 */
public class GeonameDao extends BaseDao {

    private static final String DATABASE = "geonames";
    private static final String TABLE = "geoname";
    private Connection connection;

    public GeonameDao() {
        connection = getConnection(DATABASE);
    }

    public Geoname getById(int geonameId) {
        String sql = "select * from " + TABLE + " where geonameid = " + geonameId;
        List<Geoname> geonames = query(connection, sql, null, Geoname.class);
        if (ListUtils.isEmpty(geonames))
            return null;
        return geonames.get(0);
    }

    public List<Geoname> fuzzyMatching(double latUpper, double latLower, double lngUpper, double lngLower, String fcode) {
        String sql = "select * from " + TABLE + " where latitude < ? and latitude > ? and longitude < ? and longitude > ?";
        if (fcode != null) {
            sql += " and fcode = '" + fcode + "'";
        }
        Object[] params = {latUpper, latLower, lngUpper, lngLower};
        return query(connection, sql, params, Geoname.class);
    }

    public List<Geoname> countryChildrenByFcode(String country, String fcode) {
        String sql = "select * from " + TABLE + " where country = ? and fcode = ?";
        Object[] params = {country, fcode};
        return query(connection, sql, params, Geoname.class);
    }

    public List<Geoname> countryChildrenByFclass(String country, String fclass) {
        String sql = "select * from " + TABLE + " where country = ? and fclass = ?";
        Object[] params = {country, fclass};
        return query(connection, sql, params, Geoname.class);
    }


    public static void main(String[] args) {
        GeonameDao geonameDao = new GeonameDao();
//        System.out.println(geonameDao.getById(10));
//        System.out.println(geonameDao.fuzzyMatching(32, 30, 120, 110, "ADM1").size());
        System.out.println(geonameDao.countryChildrenByFcode("CN", "ADM3").size());
    }
}
