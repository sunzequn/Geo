package com.sunzequn.geo.data.baike.dao;

import com.sunzequn.geo.data.baike.bean.InfoBoxTemplate;
import com.sunzequn.geo.data.dao.BaseDao;
import com.sunzequn.geo.data.utils.ListUtils;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Sloriac on 16/3/22.
 */
public class InfoBoxTemplateDao extends BaseDao {

    private static final String DATABASE = "baidubaike";
    private static final String TABLE = "infobox_template";
    private Connection connection;

    public InfoBoxTemplateDao() {
        this.connection = getConnection(DATABASE);
    }

    public List<InfoBoxTemplate> getAll() {
        String sql = "select * from " + TABLE;
        return query(connection, sql, null, InfoBoxTemplate.class);
    }

    public InfoBoxTemplate getById(int id) {
        String sql = "select * from " + TABLE + " where id = ?";
        Object[] params = {id};
        List<InfoBoxTemplate> infoBoxTemplateList = query(connection, sql, params, InfoBoxTemplate.class);
        if (ListUtils.isEmpty(infoBoxTemplateList)) {
            return null;
        }
        InfoBoxTemplate infoBoxTemplate = infoBoxTemplateList.get(0);
        infoBoxTemplate.initProps();
        return infoBoxTemplate;
    }


    public static void main(String[] args) {
        InfoBoxTemplateDao infoBoxTemplateDao = new InfoBoxTemplateDao();
        System.out.println(infoBoxTemplateDao.getById(70));
    }
}
