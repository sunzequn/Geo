package com.sunzequn.geo.data.baike.bdbk;

import com.sunzequn.geo.data.dao.ServerDao;
import com.sunzequn.geo.data.utils.ListUtils;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/28.
 */
public class UrlIndexDao extends ServerDao {

    private static final String DATABASE = "geokb_bdbk";
    private static final String TABLE = "url_index";
    private Connection connection;

    public UrlIndexDao() {
        connection = getConnection(DATABASE);
    }

    public UrlIndex getByUrl(String url) {
        String sql = "select * from " + TABLE + " where url = ?";
        Object[] params = {url};
        List<UrlIndex> urlIndices = query(connection, sql, params, UrlIndex.class);
        if (ListUtils.isEmpty(urlIndices)) {
            return null;
        }
        return urlIndices.get(0);
    }

    public static void main(String[] args) {
        UrlIndexDao dao = new UrlIndexDao();
        System.out.println(dao.getByUrl("/subview/2522686/16847326.htm").getId());
    }
}
