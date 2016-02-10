package com.sunzequn.geo.data.climate.pull.dao;

import com.sunzequn.geo.data.climate.pull.bean.Continent;
import com.sunzequn.geo.data.dao.BaseDao;

import java.util.List;

/**
 * Created by Sloriac on 16/1/6.
 * <p>
 * 对Continent的数据库操作
 */
public class ContinentDao extends BaseDao {

    private static final String TABLE_NAME = "climate_seed_continent";

    public ContinentDao() {
        getConnection();
    }

    public List<Continent> getAll() {
        String sql = "select * from " + TABLE_NAME;
        return query(sql, null, Continent.class);
    }

    public Continent getById(int id) {
        String sql = "select * from " + TABLE_NAME + " where id = ?";
        Object[] params = {id};
        List<Continent> continents = query(sql, params, Continent.class);
        return continents.size() > 0 ? continents.get(0) : null;
    }

    public int save(Continent continent) {
        return 1;
    }


}