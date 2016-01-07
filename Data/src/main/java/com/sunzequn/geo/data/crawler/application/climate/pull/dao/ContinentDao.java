package com.sunzequn.geo.data.crawler.application.climate.pull.dao;

import com.sunzequn.geo.data.crawler.application.climate.pull.bean.Continent;
import com.sunzequn.geo.data.dao.BaseDao;

import java.util.List;

/**
 * Created by Sloriac on 16/1/6.
 */
public class ContinentDao extends BaseDao {

    private static final String TABLE_NAME = "climate_seed_continent";

    public ContinentDao() {
        getConnection();
    }

    public List<Continent> getAll() {
        String sql = "select * from " + TABLE_NAME;
        List<Continent> continents = query(sql, null, Continent.class);
        return continents;
    }

    public Continent getById(int id) {
        String sql = "select * from " + TABLE_NAME + " where id = ?";
        Object[] params = {id};
        List<Continent> continents = query(sql, params, Continent.class);
        return continents.size() > 0 ? continents.get(0) : null;
    }


}
