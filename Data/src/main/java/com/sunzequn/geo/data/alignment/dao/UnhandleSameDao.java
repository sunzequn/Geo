package com.sunzequn.geo.data.alignment.dao;

import com.sunzequn.geo.data.alignment.bean.EquivalentClass;
import com.sunzequn.geo.data.alignment.bean.UnhandledSame;
import com.sunzequn.geo.data.dao.BaseDao;
import com.sunzequn.geo.data.utils.ListUtils;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sloriac on 16-2-28.
 */
public class UnhandleSameDao extends BaseDao {

    private static final String DATABASE = DataBaseName.database;
    private static final String TABLE = "unhandled_same";
    private Connection connection;

    public UnhandleSameDao() {
        this.connection = getConnection(DATABASE);
    }

    public int save(UnhandledSame unhandledSame) {
        String sql = "insert into " + TABLE + " values(?, ?)";
        Object[] params = {unhandledSame.getUri1(), unhandledSame.getUri2()};
        return execute(connection, sql, params);
    }

    public UnhandledSame getById(String uri1, String uri2) {
        String sql = "select * from " + TABLE + " where uri1 = ? and uri2 = ?";
        Object[] params = {uri1, uri2};
        List<UnhandledSame> unhandledSames = query(connection, sql, params, UnhandledSame.class);
        if (ListUtils.isEmpty(unhandledSames))
            return null;
        return unhandledSames.get(0);
    }
}
