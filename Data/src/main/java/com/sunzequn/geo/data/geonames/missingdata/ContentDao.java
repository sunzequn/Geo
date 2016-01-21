package com.sunzequn.geo.data.geonames.missingdata;

import com.sunzequn.geo.data.dao.BaseDao;

import java.util.List;

/**
 * Created by Sloriac on 16/1/21.
 */
public class ContentDao extends BaseDao {

    private String tableName;

    public ContentDao(String tableName) {
        this.tableName = tableName;
        getConnection("geonames_file");
    }

    public int save(Content content) {
        String sql = "insert into " + tableName + " values (?, ?)";
        Object[] params = {content.getId(), content.getContent()};
        return execute(sql, params);
    }

}
