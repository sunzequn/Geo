package com.sunzequn.geo.data.geonames.missingdata;

import com.sunzequn.geo.data.dao.BaseDao;

import java.util.List;

/**
 * Created by Sloriac on 16/1/21.
 */
public class ResourceDao extends BaseDao {

    private String tableName;

    public ResourceDao(String tableName) {
        this.tableName = tableName;
        getConnection("geonames_file");
    }

    public int save(Resource resource) {
        String sql = "insert into " + tableName + " values (?, ?)";
        Object[] params = {resource.getId(), resource.getIfvisited()};
        return execute(sql, params);
    }

    public List<Resource> getUnvisited(int limit) {
        String sql = "select * from " + tableName + " where ifvisited = 0 limit " + limit;
        return query(sql, null, Resource.class);
    }

    public List<Resource> getUnvisited() {
        String sql = "select * from " + tableName + " where ifvisited <> 1";
        return query(sql, null, Resource.class);
    }

    public List<Resource> getAll() {
        String sql = "select * from " + tableName;
        return query(sql, null, Resource.class);
    }

    public int update(int id, int ifvisited) {
        String sql = "update " + tableName + " set ifvisited = ? where id = ?";
        Object[] params = {ifvisited, id};
        return execute(sql, params);
    }

    public void close() {
        closeConnection();
    }

}
