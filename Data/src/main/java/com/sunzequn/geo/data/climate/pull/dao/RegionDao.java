package com.sunzequn.geo.data.climate.pull.dao;

import com.sunzequn.geo.data.climate.pull.bean.Region;
import com.sunzequn.geo.data.dao.BaseDao;

import java.util.List;

/**
 * Created by Sloriac on 16/1/7.
 */
public class RegionDao extends BaseDao {

    private static final String TABLE_NAME = "climate_seed_region";

    public RegionDao() {
        getConnection();
    }

    public int save(Region region) {
        String sql = "insert into " + TABLE_NAME + " values (?, ?, ?, ?, ?)";
        Object[] params = {region.getId(), region.getName(), region.getUrl(), region.getParentid(), region.getIfvisited()};
        return execute(sql, params);
    }

    public List<Region> getAll() {
        String sql = "select * from " + TABLE_NAME;
        return query(sql, null, Region.class);
    }

    public List<Region> getUnvisited() {
        String sql = "select * from " + TABLE_NAME + " where ifvisited = 0";
        return query(sql, null, Region.class);
    }

    public int update(int id, int ifvisited) {
        String sql = "update " + TABLE_NAME + " set ifvisited = ? where id = ?";
        Object[] params = {ifvisited, id};
        return execute(sql, params);
    }
}
