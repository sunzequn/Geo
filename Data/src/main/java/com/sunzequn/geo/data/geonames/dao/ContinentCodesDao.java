package com.sunzequn.geo.data.geonames.dao;

import com.sunzequn.geo.data.dao.BaseDao;
import com.sunzequn.geo.data.geonames.bean.ContinentCodes;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sloriac on 16-2-17.
 */
public class ContinentCodesDao extends BaseDao {
    private static final String DATABASE = "geonames";
    private static final String TABLE = "continentCodes";
    private Connection connection;

    public ContinentCodesDao() {
        connection = getConnection(this.DATABASE);
    }

    public List<ContinentCodes> getAll() {
        String sql = "select * from " + TABLE;
        return query(connection, sql, null, ContinentCodes.class);
    }

    public ContinentCodes getById(int id) {
        String sql = "select * from " + TABLE + " where geonameid = " + id;
        List<ContinentCodes> continentCodes = query(connection, sql, null, ContinentCodes.class);
        if (continentCodes == null) {
            return null;
        }
        return continentCodes.get(0);
    }

    public static void main(String[] args) {
        ContinentCodesDao continentCodesDao = new ContinentCodesDao();
        System.out.println(continentCodesDao.getById(6255146).getName());
    }
}
