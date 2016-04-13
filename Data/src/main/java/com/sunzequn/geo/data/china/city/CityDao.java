package com.sunzequn.geo.data.china.city;

import com.sunzequn.geo.data.dao.BaseDao;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Sloriac on 15/12/30.
 */
public class CityDao extends BaseDao {

    private static final String DATABASE = "geonames";
    private static final String TABLE = "china_city";
    private Connection connection;
    public CityDao() {
        connection = getConnection(DATABASE);
    }

    public int save(ChinaCity city) {
        String sql = "insert into china_city values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = {city.getId(), city.getName(), city.getShortName(), city.getMergerName(),
                city.getPinyin(), city.getCityCode(), city.getZipCode(), city.getParentId(),
                city.getLevelType(), city.getLng(), city.getLat()};
        return execute(connection, sql, params);
    }

    public List<ChinaCity> getCityById(int id) {
        String sql = "select * from china_city where id = ?";
        Object[] params = {id};
        return query(connection, sql, params, ChinaCity.class);
    }

    public List<ChinaCity> getCityByName(String name) {
        String sql = "select * from china_city where name = ?";
        Object[] params = {name};
        return query(connection, sql, params, ChinaCity.class);
    }

    public List<ChinaCity> getCityByLevel(int level) {
        String sql = "select * from china_city where leveltype = ?";
        Object[] params = {level};
        return query(connection, sql, params, ChinaCity.class);
    }

    public List<ChinaCity> getCityByParent(int parentId) {
        String sql = "select * from china_city where parentid = ?";
        Object[] params = {parentId};
        return query(connection, sql, params, ChinaCity.class);
    }

    public int deleteById(int id) {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "delete from china_city where id = ?";
        Object[] params = {id};
        return execute(connection, sql, params);
    }

    public int updateBadData(int id, String mergerName, int parentId, int levelType) {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "update china_city set mergername = ?, parentid = ?, leveltype = ? where id = ?";
        Object[] params = {mergerName, parentId, levelType, id};
        return execute(connection, sql, params);

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
