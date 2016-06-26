package com.sunzequn.geo.data.china.geo;

import com.sunzequn.geo.data.dao.BaseDao;
import com.sunzequn.geo.data.utils.ListUtils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public List<ChinaCity> getAll() {
        String sql = "select * from " + TABLE;
        return query(connection, sql, null, ChinaCity.class);
    }

    public List<ChinaCity> getByLevel(int level) {
        String sql = "select * from " + TABLE + " where leveltype = ?";
        Object[] params = {level};
        return query(connection, sql, params, ChinaCity.class);
    }

    public List<ChinaCity> getEndWith(String suffix) {
        String sql = "select * from " + TABLE + " where name like '%" + suffix + "'";
        return query(connection, sql, null, ChinaCity.class);
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
        if (ListUtils.isEmpty(res) && !ListUtils.isEmpty(shortCities)) {
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

    public Set<String> getChinaCityLongName() {
        List<ChinaCity> chinaCities = getAll();
        Set<String> names = new HashSet<>();
        for (ChinaCity chinaCity : chinaCities) {
            names.add(chinaCity.getName());
        }
        return names;
    }

    public Set<String> getChinaCityShortName() {
        List<ChinaCity> chinaCities = getAll();
        Set<String> names = new HashSet<>();
        for (ChinaCity chinaCity : chinaCities) {
            names.add(chinaCity.getShortname());
        }
        return names;
    }

    public static void main(String[] args) {
        ChinaCityDao dao = new ChinaCityDao();
//        System.out.println(dao.getByLevel(1));
        System.out.println(dao.getEndWith("县"));
    }

}
