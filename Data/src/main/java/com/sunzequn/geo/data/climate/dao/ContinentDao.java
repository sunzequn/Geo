package com.sunzequn.geo.data.climate.dao;

import com.sunzequn.geo.data.climate.bean.Continent;
import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Sloriac on 16/1/6.
 * <p>
 * 对Continent的数据库操作
 */
public class ContinentDao extends BaseDao {
    private static final String DATABASE = "geocities";
    private static final String TABLE_NAME = "climate_seed_continent";
    private Connection connection;

    public ContinentDao() {
        connection = getConnection(DATABASE);
    }

    public List<Continent> getAll() {
        String sql = "select * from " + TABLE_NAME;
        return query(connection, sql, null, Continent.class);
    }

    public Continent getById(int id) {
        String sql = "select * from " + TABLE_NAME + " where id = ?";
        Object[] params = {id};
        List<Continent> continents = query(connection, sql, params, Continent.class);
        return continents.size() > 0 ? continents.get(0) : null;
    }

    public int save(Continent continent) {
        return 1;
    }


}
