package com.sunzequn.geo.data.geonames.filebean;

import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Sloriac on 16/1/21.
 */
public class ContentDao extends BaseDao {

    private String tableName;
    private Connection connection;

    public ContentDao(String tableName) {
        this.tableName = tableName;
        connection = getConnection("geonames_file");
    }

    public int save(Content content) {
        String sql = "insert into " + tableName + " values (?, ?)";
        Object[] params = {content.getId(), content.getContent()};
        return execute(connection, sql, params);
    }

    public List<Content> getAll() {
        String sql = "select * from " + tableName + " limit 10000";
        return query(connection, sql, null, Content.class);
    }

    public void close() {
        closeConnection(connection);
    }

}
