package com.sunzequn.geo.data.geonamesplus.handler;

import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sloriac on 16-12-5.
 */
public class NamesMappingDao extends BaseDao {

    private static final String DATABASE = "geonames_new";
    private String TABLE;
    private Connection connection;

    public NamesMappingDao(String table) {
        TABLE = table;
        connection = getConnection(DATABASE);
    }

    public int[] addBatch(List<NamesMapping> mappings) {
        String sql = "insert into " + TABLE + " (geonameid, name) values (?, ?)";
        Object[][] parmas = new Object[mappings.size()][2];
        for (int i = 0; i < mappings.size(); i++) {
            NamesMapping mapping = mappings.get(i);
            parmas[i][0] = mapping.getGeonameid();
            parmas[i][1] = mapping.getName();
        }
        return batch(connection, sql, parmas);
    }
}
