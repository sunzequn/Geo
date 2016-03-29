package com.sunzequn.geo.data.geonames.statistic;

import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Sloriac on 16/3/23.
 */
public class StatisticInfoDao extends BaseDao {

    private static final String DATABASE = "geonames";
    private static final String TABLE = "statistic";
    private Connection connection;

    public StatisticInfoDao() {
        this.connection = getConnection(DATABASE);
    }

    public int save(StatisticInfo info) {
        String sql = "insert into " + TABLE + " values (?, ?, ?, ?)";
        Object[] params = {info.getFcode(), info.getNum(), info.getName(), info.getDescription()};
        return execute(connection, sql, params);
    }

    public static void main(String[] args) {
        StatisticInfoDao dao = new StatisticInfoDao();
        dao.save(new StatisticInfo("s", 1, "ss", "ss"));
    }
}
