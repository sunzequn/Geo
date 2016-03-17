package com.sunzequn.geo.data.baike.infobox;

import com.sunzequn.geo.data.alignment.dao.DataBaseName;
import com.sunzequn.geo.data.baike.bean.InfoBox;
import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Sloriac on 16/3/17.
 */
public class InfoBoxTempDao extends BaseDao {

    private static final String DATABASE = "baidubaike";
    private static final String TABLE = "infobox_template";
    private Connection connection;

    public InfoBoxTempDao() {
        this.connection = getConnection(DATABASE);
    }

    public List<InfoBoxTemplate> getAll() {
        String sql = "select * from " + TABLE;
        return query(connection, sql, null, InfoBoxTemplate.class);
    }
}
