package com.sunzequn.geo.data.gpcp;

import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sunzequn on 2016/6/26.
 */
public class PrecipitationDao extends BaseDao {

    private static final String DATABASE = "climate";
    private static final String TABLE = "precipitation_monthly";
    private Connection connection;

    public PrecipitationDao() {
        this.connection = getConnection(DATABASE);
    }

    public int save(Precipitation precipitation) {
        String sql = "insert into " + TABLE + " (longitude, latitude, year, month, precipitation) values (?, ?, ?, ?, ?)";
        Object[] params = {precipitation.getLongitude(), precipitation.getLatitude(), precipitation.getYear(), precipitation.getMonth(), precipitation.getPrecipitation()};
        return execute(connection, sql, params);
    }

    public int[] saveBatch(List<Precipitation> precipitations) {

        String sql = "insert into " + TABLE + " (longitude, latitude, year, month, precipitation) values (?, ?, ?, ?, ?)";
        Object[][] params = new Object[precipitations.size()][];
        for (int i = 0; i < precipitations.size(); i++) {
            Precipitation precipitation = precipitations.get(i);
            params[i] = new Object[5];
            params[i][0] = precipitation.getLongitude();
            params[i][1] = precipitation.getLatitude();
            params[i][2] = precipitation.getYear();
            params[i][3] = precipitation.getMonth();
            params[i][4] = precipitation.getPrecipitation();
        }
        return batch(connection, sql, params);
    }
}
