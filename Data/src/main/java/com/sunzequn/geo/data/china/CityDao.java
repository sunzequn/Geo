package com.sunzequn.geo.data.china;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Sloriac on 15/12/30.
 */
public class CityDao {

    private static final String CLASS_NAME = "com.mysql.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/geocities?useUnicode=true&characterEncoding=UTF-8";
    private static Connection conn;

    public CityDao() {
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

    public int save(ChinaCity city) {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "insert into china_city values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = {city.getId(), city.getName(), city.getShortName(), city.getMergerName(),
                city.getPinyin(), city.getCityCode(), city.getZipCode(), city.getParentId(),
                city.getLevelType(), city.getLng(), city.getLat()};
        try {
            int res = queryRunner.update(conn, sql, params);
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<ChinaCity> getCityById(int id) {
        String sql = "select * from china_city where id = ?";
        Object[] params = {id};
        return query(sql, params);
    }

    public List<ChinaCity> getCityByName(String name) {
        String sql = "select * from china_city where name = ?";
        Object[] params = {name};
        return query(sql, params);
    }

    public List<ChinaCity> getCityByLevel(int level) {
        String sql = "select * from china_city where leveltype = ?";
        Object[] params = {level};
        return query(sql, params);
    }

    public List<ChinaCity> getCityByParent(int parentId) {
        String sql = "select * from china_city where parentid = ?";
        Object[] params = {parentId};
        return query(sql, params);
    }

    private List<ChinaCity> query(String sql, Object[] params) {
        QueryRunner queryRunner = new QueryRunner();
        try {
            List<ChinaCity> chinacities = queryRunner.query(conn, sql, new BeanListHandler<ChinaCity>(ChinaCity.class), params);
            if (chinacities != null && chinacities.size() > 0) {
                return chinacities;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int deleteById(int id) {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "delete from china_city where id = ?";
        Object[] params = {id};
        try {
            return queryRunner.update(conn, sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int updateBadData(int id, String mergerName, int parentId, int levelType) {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "update china_city set mergername = ?, parentid = ?, leveltype = ? where id = ?";
        Object[] params = {mergerName, parentId, levelType, id};
        try {
            return queryRunner.update(conn, sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public static void main(String[] args) {
        CityDao cityDao = new CityDao();
//        System.out.println(cityDao.getCityByLevel(1).size());
//        System.out.println(cityDao.getCityByParent(110100).size());
//        System.out.println(cityDao.getCityById(429004));
//        System.out.println(cityDao.getCityByName("直辖县级").size());
//        cityDao.deleteById(1);

//        String s= "中国,河南省,直辖县级,济源市";
//        String s1 = s.replace(",直辖县级", "");
//        System.out.println(s1);

//        cityDao.updateBadData(1, "szq", 1, 1);
    }


}
