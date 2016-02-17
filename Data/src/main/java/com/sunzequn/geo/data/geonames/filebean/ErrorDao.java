package com.sunzequn.geo.data.geonames.filebean;

import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;

/**
 * Created by sloriac on 16-1-24.
 */
public class ErrorDao extends BaseDao {

    private static final String TABLENAME = "error";
    private Connection connection;

    public ErrorDao() {
        connection = getConnection("geonames_file");
    }

    public int save(Error error) {
        String sql = "insert into " + TABLENAME + " values (?)";
        Object[] params = {error.getId()};
        return execute(connection, sql, params);
    }

    public int delete(int id) {
        String sql = "delete from " + TABLENAME + " where id = ?";
        Object[] params = {id};
        return execute(connection, sql, params);
    }
}
