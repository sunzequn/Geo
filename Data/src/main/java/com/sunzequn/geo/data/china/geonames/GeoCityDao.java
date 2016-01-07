package com.sunzequn.geo.data.china.geonames;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Sloriac on 15/12/31.
 */
public class GeoCityDao {

    private static final String CLASS_NAME = "com.mysql.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/geocities?useUnicode=true&characterEncoding=UTF-8";
    private static Connection conn;

    public GeoCityDao() {
        getConnection();
    }

    private void getConnection() {
        try {
            Class.forName(CLASS_NAME);
            conn = DriverManager.getConnection(JDBC_URL, "root", "root");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int save(GeoCity city) {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "insert into china_city_geonames values (?, ?, ?)";
        Object[] params = {city.getGeonamesid(), city.getName(), city.getParentid()};
        try {
            int res = queryRunner.update(conn, sql, params);
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<GeoCity> getById(int id) {
        String sql = "select  * from china_city_geonames where geonamesid = ?";
        Object[] params = {id};
        return query(sql, params);
    }

    public List<GeoCity> getByParentId(int id) {
        String sql = "select  * from china_city_geonames where parentid = ?";
        Object[] params = {id};
        return query(sql, params);
    }

    public List<GeoCity> getAll() {
        String sql = "select  * from china_city_geonames";
        return query(sql, null);
    }

    private List<GeoCity> query(String sql, Object[] params) {
        QueryRunner queryRunner = new QueryRunner();
        try {
            List<GeoCity> cities = queryRunner.query(conn, sql, new BeanListHandler<GeoCity>(GeoCity.class), params);
            if (cities != null && cities.size() > 0) {
                return cities;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int updateName(int id, String name) {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "update china_city_geonames set name = ? where geonamesid = ?";
        Object[] params = {name, id};
        try {
            return queryRunner.update(conn, sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        GeoCityDao cityDao = new GeoCityDao();
//        System.out.println(cityDao.getAll().size());

        cityDao.updateName(1279433, "孙泽群");

    }

}
