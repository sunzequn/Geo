package com.sunzequn.geo.data.baike.bdbk;

import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/27.
 */
public class RedirectUrlDao extends BaseDao {

    private static final String DATABASE = "baidubaike";
    private static String TABLE = "url_type_redirect";
    private Connection connection;

    public RedirectUrlDao() {
        connection = getConnection(DATABASE);
    }

    public List<RedirectUrl> getAll() {
        String sql = "select * from " + TABLE;
        return query(connection, sql, null, RedirectUrl.class);
    }

    public int add(RedirectUrl redirect) {
        String sql = "insert into " + TABLE + " (url, redirect_url, type) values (?, ?, ?)";
        Object[] parmas = {redirect.getUrl(), redirect.getRedirect_url(), redirect.getType()};
        return execute(connection, sql, parmas);
    }

    public int[] addBatch(List<RedirectUrl> redirectUrls) {
        String sql = "insert into " + TABLE + " (url, redirect_url, type) values (?, ?, ?)";
        Object[][] parmas = new Object[redirectUrls.size()][3];
        for (int i = 0; i < redirectUrls.size(); i++) {
            RedirectUrl redirectUrl = redirectUrls.get(i);
            parmas[i][0] = redirectUrl.getUrl();
            parmas[i][1] = redirectUrl.getRedirect_url();
            parmas[i][2] = redirectUrl.getType();

        }
        return batch(connection, sql, parmas);
    }
}
