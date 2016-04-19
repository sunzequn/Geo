package com.sunzequn.geo.data.baike.bdbk;

import com.sunzequn.geo.data.dao.ServerDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/18.
 */
public class TagDao extends ServerDao {

    private static final String DATABASE = "geokb_bdbk";
    private static final String TABLE = "open_tag";
    private Connection connection;

    public TagDao() {
        connection = getConnection(DATABASE);
    }

    public List<Tag> getByUrl(String url) {
        String sql = "select * from " + TABLE + " where url = ?";
        Object[] params = {url};
        return query(connection, sql, params, Tag.class);
    }

    public static void main(String[] args) {
        TagDao tagDao = new TagDao();
        System.out.println(tagDao.getByUrl("/view/2524260.htm"));
    }
}
