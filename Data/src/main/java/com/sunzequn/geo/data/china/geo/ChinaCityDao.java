package com.sunzequn.geo.data.china.geo;

import com.sunzequn.geo.data.dao.BaseDao;
import com.sunzequn.geo.data.utils.ListUtils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloriac on 16/3/9.
 */
public class ChinaCityDao extends BaseDao {

    private static final String DATABASE = "geocities";
    private static final String TABLE = "china_city";
    private Connection connection;

    public ChinaCityDao() {
        connection = getConnection(DATABASE);
    }

    public ChinaCity getById(int id) {
        String sql = "select * from " + TABLE + " where id = " + id;
        List<ChinaCity> chinaCities = query(connection, sql, null, ChinaCity.class);
        if (chinaCities == null) {
            return null;
        }
        return chinaCities.get(0);
    }

    public List<ChinaCity> getByName(String name) {
        List<ChinaCity> fullCities = getByFullName(name);
        List<ChinaCity> shortCities = getByShortName(name);
        List<ChinaCity> res = new ArrayList<>();
        if (!ListUtils.isEmpty(fullCities)) {
            res.addAll(fullCities);
        }
        if (!ListUtils.isEmpty(shortCities)) {
            res.addAll(shortCities);
        }
        if (res.size() == 0) {
            return null;
        }
        return res;
    }

    public List<ChinaCity> getByFullName(String name) {
        String sql = "select * from " + TABLE + " where name = ?";
        Object[] params = {name};
        return query(connection, sql, params, ChinaCity.class);
    }

    public List<ChinaCity> getByShortName(String name) {
        String sql = "select * from " + TABLE + " where shortname = ?";
        Object[] params = {name};
        return query(connection, sql, params, ChinaCity.class);
    }

    public List<ChinaCity> getChildren(int id) {
        String sql = "select * from " + TABLE + " where parentid = " + id;
        return query(connection, sql, null, ChinaCity.class);
    }

}
