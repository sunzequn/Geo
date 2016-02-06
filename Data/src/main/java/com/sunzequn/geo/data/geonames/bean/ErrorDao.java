package com.sunzequn.geo.data.geonames.bean;

import com.sunzequn.geo.data.dao.BaseDao;

/**
 * Created by sloriac on 16-1-24.
 */
public class ErrorDao extends BaseDao {

    private static final String TABLENAME = "error";

    public ErrorDao() {
        getConnection("geonames_file");
    }

    public int save(Error error) {
        String sql = "insert into " + TABLENAME + " values (?)";
        Object[] params = {error.getId()};
        return execute(sql, params);
    }
}
