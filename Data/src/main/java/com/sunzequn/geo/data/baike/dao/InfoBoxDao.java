package com.sunzequn.geo.data.baike.dao;

import com.sunzequn.geo.data.baike.bean.InfoBox;
import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Sloriac on 16/3/15.
 */
public class InfoBoxDao extends BaseDao {

    private static final String DATABASE = "baidubaike";
    private static final String TABLE = "infobox";
    private Connection connection;

    public InfoBoxDao() {
        connection = getConnection(DATABASE);
    }

    public List<InfoBox> getByTitle(String title) {
        String sql = "select * from " + TABLE + " where title = ?";
        Object[] params = {title};
        return query(connection, sql, params, InfoBox.class);
    }
}
