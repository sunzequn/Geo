package com.sunzequn.geo.data.baike.bdbk;

import com.sunzequn.geo.data.dao.ServerDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/27.
 */
public class RedirectDao extends ServerDao {

    private static final String DATABASE = "geokb_bdbk";
    private static final String TABLE = "baidu_redirect";
    private Connection connection;

    public RedirectDao() {
        this.connection = getConnection(DATABASE);
    }

    public List<Redirect> getAll() {
        String sql = "select * from " + TABLE;
        return query(connection, sql, null, Redirect.class);
    }

    public List<Redirect> getBySrc(String url) {
        String sql = "select * from " + TABLE + " where src = ?";
        Object[] params = {url};
        return query(connection, sql, params, Redirect.class);
    }

    public List<Redirect> getByDest(String url) {
        String sql = "select * from " + TABLE + " where dest = ?";
        Object[] params = {url};
        return query(connection, sql, params, Redirect.class);
    }

}
