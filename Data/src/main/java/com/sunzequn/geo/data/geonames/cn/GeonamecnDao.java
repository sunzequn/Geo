package com.sunzequn.geo.data.geonames.cn;

import com.sunzequn.geo.data.dao.BaseDao;
import com.sunzequn.geo.data.gpcp.Precipitation;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sunzequn on 2016/6/27.
 */
public class GeonamecnDao extends BaseDao {

    private static final String DATABASE = "geonames";
    private static final String TABLE = "geoname_cn";
    private Connection connection;

    public GeonamecnDao() {
        connection = getConnection(DATABASE);
    }

    public int[] saveBatch(List<Geonamecn> geonamecns) {
        String sql = "insert into " + TABLE + " (geonameid, zhname) values (?, ?)";
        Object[][] params = new Object[geonamecns.size()][];
        for (int i = 0; i < geonamecns.size(); i++) {
            Geonamecn geonamecn = geonamecns.get(i);
            params[i] = new Object[2];
            params[i][0] = geonamecn.getGeonameid();
            params[i][1] = geonamecn.getZhname();
        }
        return batch(connection, sql, params);
    }
}
