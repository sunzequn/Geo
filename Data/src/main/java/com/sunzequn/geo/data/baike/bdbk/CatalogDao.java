package com.sunzequn.geo.data.baike.bdbk;

import com.sunzequn.geo.data.dao.BaseDao;
import com.sunzequn.geo.data.dao.ServerDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/14.
 */
public class CatalogDao extends ServerDao {

    private static final String DATABASE = "geokb_bdbk";
    private static final String TABLE = "catalog";
    private Connection connection;

    public CatalogDao() {
        connection = getConnection(DATABASE);
    }

    public List<Catalog> getByUrl(String url) {
        String sql = "select * from " + TABLE + " where url = ?";
        Object[] params = {url};
        return query(connection, sql, params, Catalog.class);
    }
}
