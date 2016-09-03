package com.sunzequn.geo.data.baike.bdbk;

import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/27.
 */
public class JingweiduDao extends BaseDao {

    private static final String DATABASE = "baidubaike";
    private static final String TABLE = "url_type_jingweidu";
    private Connection connection;

    public JingweiduDao() {
        connection = getConnection(DATABASE);
    }


    public List<Jingweidu> getAll() {
        String sql = "select * from " + TABLE;
        return query(connection, sql, null, Jingweidu.class);
    }


    public int add(Jingweidu jingweidu) {
        String sql = "insert into " + TABLE + " (url, lng, lat) values (?, ?, ?)";
        Object[] parmas = {jingweidu.getUrl(), jingweidu.getLng(), jingweidu.getLat()};
        return execute(connection, sql, parmas);
    }

    public int[] addBatch(List<Jingweidu> jingweidus) {
        String sql = "insert into " + TABLE + " (url, lng, lat) values (?, ?, ?)";
        Object[][] parmas = new Object[jingweidus.size()][3];
        for (int i = 0; i < jingweidus.size(); i++) {
            Jingweidu jingweidu = jingweidus.get(i);
            parmas[i][0] = jingweidu.getUrl();
            parmas[i][1] = jingweidu.getLng();
            parmas[i][2] = jingweidu.getLat();
        }
        return batch(connection, sql, parmas);
    }

    public int[] updateBatch(List<Jingweidu> jingweidus, List<String> oldUrls) {
        String sql = "update " + TABLE + " set url = ? where url = ?";
        Object[][] parmas = new Object[jingweidus.size()][2];
        for (int i = 0; i < jingweidus.size(); i++) {
            Jingweidu jingweidu = jingweidus.get(i);
            String oldUrl = oldUrls.get(i);
            parmas[i][0] = jingweidu.getUrl();
            parmas[i][1] = oldUrl;
        }
        return batch(connection, sql, parmas);
    }
}
