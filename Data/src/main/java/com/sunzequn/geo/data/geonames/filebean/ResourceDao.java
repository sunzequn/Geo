package com.sunzequn.geo.data.geonames.filebean;

import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Sloriac on 16/1/21.
 */
public class ResourceDao extends BaseDao {

    private String tableName;
    private Connection connection;

    public ResourceDao(String tableName) {
        this.tableName = tableName;
        connection = getConnection("geonames_file");
    }

    public int save(Resource resource) {
        String sql = "insert into " + tableName + " values (?, ?)";
        Object[] params = {resource.getId(), resource.getIfvisited()};
        return execute(connection, sql, params);
    }

    public List<Resource> getUnvisited(int limit) {
        String sql = "select * from " + tableName + " where ifvisited = 0 limit " + limit;
        return query(connection, sql, null, Resource.class);
    }

    public List<Resource> getUnvisited() {
        String sql = "select * from " + tableName + " where ifvisited <> 1";
        return query(connection, sql, null, Resource.class);
    }

    public List<Resource> getAll() {
        String sql = "select * from " + tableName;
        return query(connection, sql, null, Resource.class);
    }

    public int update(int id, int ifvisited) {
        String sql = "update " + tableName + " set ifvisited = ? where id = ?";
        Object[] params = {ifvisited, id};
        return execute(connection, sql, params);
    }

    public void close() {
        closeConnection(connection);
    }

}
