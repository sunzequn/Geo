package com.sunzequn.geo.data.climate.china;

import com.sunzequn.geo.data.china.geo.ChinaCity;
import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;

/**
 * Created by sunzequn on 2016/7/22.
 */
public class ChinaClimateFactorDao extends BaseDao {

    private static final String DATABASE = "climate";
    private static final String TABLE_NAME = "china_climate_factor";
    private Connection connection;

    public ChinaClimateFactorDao() {
        connection = getConnection(DATABASE);
    }

    public int save(ChinaClimateFactor factor) {
        String sql = "insert into " + TABLE_NAME + " (" +
                "V01000, V04001, V04002, V04003, V11002, " +
                "V11042, V11212, V11041, V11043, V14032," +
                "V10004, V10201, V10202, V12001, V12052, " +
                "V12053, V13004, V13003, V13007,V13201) " +
                "values (" +
                "?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?)";
        Object[] params = {
                factor.getV01000(), factor.getV04001(), factor.getV04002(), factor.getV04003(), factor.getV11002(),
                factor.getV11042(), factor.getV11212(), factor.getV11041(), factor.getV11043(), factor.getV14032(),
                factor.getV10004(), factor.getV10201(), factor.getV10202(), factor.getV12001(), factor.getV12052(),
                factor.getV12053(), factor.getV13004(), factor.getV13003(), factor.getV13007(), factor.getV13201()};
        return execute(connection, sql, params);
    }

    public static void main(String[] args) {
        ChinaClimateFactor factor = new ChinaClimateFactor();
        ChinaClimateFactorDao dao = new ChinaClimateFactorDao();
        dao.save(factor);
    }
}
