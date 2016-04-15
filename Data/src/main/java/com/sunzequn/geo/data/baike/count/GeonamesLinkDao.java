package com.sunzequn.geo.data.baike.count;

import com.sunzequn.geo.data.dao.BaseDao;
import com.sunzequn.geo.data.geonames.GeoNameUtil;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/15.
 */
public class GeonamesLinkDao extends BaseDao {

    private static final String DATABASE = "baidubaike";
    private static final String TABLE = "geonames";
    private Connection connection;

    public GeonamesLinkDao() {
        connection = getConnection(DATABASE);
    }

    public List<GeonamesLink> getByType(String type) {
        String sql = "select * from " + TABLE + " where type = ?";
        Object[] params = {type};
        return query(connection, sql, params, GeonamesLink.class);
    }

    public static void main(String[] args) {
        GeonamesLinkDao dao = new GeonamesLinkDao();
        System.out.println(dao.getByType("省"));
    }

}
