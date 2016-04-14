package com.sunzequn.geo.data.baike.bdbk;

import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/12.
 */
public class UrlTypeLocationDao extends BaseDao {
    private static final String DATABASE = "baidubaike";
    private static String TABLE = "url_type_location";
    private Connection connection;

    public UrlTypeLocationDao() {
        connection = getConnection(DATABASE);
    }

    public List<UrlTypeLocation> getAllUrl() {
        String sql = "select url from " + TABLE;
        return query(connection, sql, null, UrlTypeLocation.class);
    }

    public int add(UrlTypeLocation urlTypeLocation) {
        String sql = "insert into " + TABLE + " (url, type, title, lng, lat, precise, confidence, level) values (?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] parmas = {urlTypeLocation.getUrl(), urlTypeLocation.getType(), urlTypeLocation.getTitle(), urlTypeLocation.getLng(), urlTypeLocation.getLat(),
                urlTypeLocation.getPrecise(), urlTypeLocation.getConfidence(), urlTypeLocation.getLevel()};
        return execute(connection, sql, parmas);
    }

    public int[] addBatch(List<UrlTypeLocation> urlTypeLocations) {
        String sql = "insert into " + TABLE + " (url, type, title, lng, lat, precise, confidence, level) values (?, ?, ?, ?, ?, ?, ?, ?)";
        Object[][] parmas = new Object[urlTypeLocations.size()][8];
        for (int i = 0; i < urlTypeLocations.size(); i++) {
            UrlTypeLocation urlTypeLocation = urlTypeLocations.get(i);
            parmas[i][0] = urlTypeLocation.getUrl();
            parmas[i][1] = urlTypeLocation.getType();
            parmas[i][2] = urlTypeLocation.getTitle();
            parmas[i][3] = urlTypeLocation.getLng();
            parmas[i][4] = urlTypeLocation.getLat();
            parmas[i][5] = urlTypeLocation.getPrecise();
            parmas[i][6] = urlTypeLocation.getConfidence();
            parmas[i][7] = urlTypeLocation.getLevel();
        }
        return batch(connection, sql, parmas);
    }
}
