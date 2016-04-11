package com.sunzequn.geo.data.baike.bdbk;

import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;

/**
 * Created by sunzequn on 2016/4/11.
 */
public class UrlTypeDao extends BaseDao {

    private static final String DATABASE = "baidubaike";
    private static final String TABLE = "url_type";
    private Connection connection;

    public UrlTypeDao() {
        connection = getConnection(DATABASE);
    }

    public int addType(String url, String type, int confidence) {
        String sql = "insert into " + TABLE + " (url, type, confidence) values (?, ?, ?)";
        Object[] parmas = {url, type, confidence};
        return execute(connection, sql, parmas);
    }

    public static void main(String[] args) {
        UrlTypeDao dao = new UrlTypeDao();
        dao.addType("test", "cejs", 1);
    }
}
