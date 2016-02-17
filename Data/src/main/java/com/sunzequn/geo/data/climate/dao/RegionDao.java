package com.sunzequn.geo.data.climate.dao;

import com.sunzequn.geo.data.climate.bean.Country;
import com.sunzequn.geo.data.climate.bean.Region;
import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Sloriac on 16/1/7.
 */
public class RegionDao extends BaseDao {
    private static final String DATABASE = "geocities";
    private static final String TABLE_NAME = "climate_seed_region";
    private Connection connection;

    public RegionDao() {
        connection = getConnection(DATABASE);
    }

    public int save(Region region) {
        String sql = "insert into " + TABLE_NAME + " values (?, ?, ?, ?, ?, ?)";
        Object[] params = {region.getId(), region.getName(), region.getUrl(), region.getParentid(), region.getIfvisited(), region.getMatch()};
        return execute(connection, sql, params);
    }

    public List<Region> getAll() {
        String sql = "select * from " + TABLE_NAME;
        return query(connection, sql, null, Region.class);
    }

    public List<Region> getByParentId(int id) {
        String sql = "select * from " + TABLE_NAME + " where parentid = " + id;
        return query(connection, sql, null, Region.class);
    }

    public List<Region> getUnvisited() {
        String sql = "select * from " + TABLE_NAME + " where ifvisited = 0";
        return query(connection, sql, null, Region.class);
    }

    public List<Region> getUnmatched() {
        String sql = "select * from " + TABLE_NAME + " where match = 0";
        return query(connection, sql, null, Region.class);
    }

    public int updateMatch(int id, int match) {
        String sql = "update " + TABLE_NAME + " set match = ? where id = ?";
        Object[] params = {match, id};
        return execute(connection, sql, params);
    }

    public int update(int id, int ifvisited) {
        String sql = "update " + TABLE_NAME + " set ifvisited = ? where id = ?";
        Object[] params = {ifvisited, id};
        return execute(connection, sql, params);
    }

    public static void main(String[] args) {
//        Region region = new Region(0, "name", "url", 136, 0);
        RegionDao regionDao = new RegionDao();
//        regionDao.save(region);
//        region.setName("name2");
//        regionDao.save(region);
        System.out.println(regionDao.getByParentId(136));

    }
}
