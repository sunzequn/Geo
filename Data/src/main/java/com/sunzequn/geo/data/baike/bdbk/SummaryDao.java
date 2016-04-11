package com.sunzequn.geo.data.baike.bdbk;

import com.sunzequn.geo.data.dao.ServerDao;
import com.sunzequn.geo.data.utils.ListUtils;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/11.
 */
public class SummaryDao extends ServerDao {

    private static final String DATABASE = "geokb_bdbk";
    private static final String TABLE = "summary";
    private Connection connection;

    public SummaryDao() {
        connection = getConnection(DATABASE);
    }

    public Summary getByUrl(String url) {
        String sql = "select * from " + TABLE + " where url = ?";
        Object[] params = {url};
        List<Summary> summaries = query(connection, sql, params, Summary.class);
        if (ListUtils.isEmpty(summaries)) {
            return null;
        }
        return summaries.get(0);
    }
}
