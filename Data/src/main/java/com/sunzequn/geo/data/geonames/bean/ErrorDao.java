package com.sunzequn.geo.data.geonames.bean;

import com.sunzequn.geo.data.dao.BaseDao;

/**
 * Created by sloriac on 16-1-24.
 */
public class ErrorDao extends BaseDao {

    private String tableName;

    public ErrorDao(String tableName) {
        this.tableName = tableName;
        getConnection("geonames_file");
    }

    public int save(Error error) {
        String sql = "insert into " + tableName + " values (?)";
        Object[] params = {error.getId()};
        return execute(sql, params);
    }
}
