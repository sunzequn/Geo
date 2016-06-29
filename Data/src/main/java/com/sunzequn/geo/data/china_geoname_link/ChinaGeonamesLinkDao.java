package com.sunzequn.geo.data.china_geoname_link;

import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sunzequn on 2016/6/28.
 */
public class ChinaGeonamesLinkDao extends BaseDao {

    private static final String DATABASE = "geonames";
    private static final String TABLE = "china_city_geoname_link";
    private Connection connection;

    public ChinaGeonamesLinkDao() {
        connection = getConnection(DATABASE);
    }

    public int save(ChinaGeonamesLink link) {
        String sql = "insert into " + TABLE + " (cityid, geonameid, leveltype, confidence) values (?, ?, ?, ?)";
        Object[] params = {link.getCityid(), link.getGeonameid(), link.getLeveltype(), link.getConfidence()};
        return execute(connection, sql, params);
    }

    public List<ChinaGeonamesLink> getByLevel(String level) {
        String sql = "select * from " + TABLE + " where leveltype = ?";
        Object[] params = {level};
        return query(connection, sql, params, ChinaGeonamesLink.class);
    }

    public static void main(String[] args) {
        ChinaGeonamesLinkDao dao = new ChinaGeonamesLinkDao();
        dao.save(new ChinaGeonamesLink(1, 2, "1", 1));
    }
}
