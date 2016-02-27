package com.sunzequn.geo.data.alignment.dao;

import com.sunzequn.geo.data.alignment.bean.EquivalentClass;
import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;

/**
 * Created by Sloriac on 16/2/27.
 */
public class EquivalentClassDao extends BaseDao {

    private static final String DATABASE = "alignment";
    private static final String TABLE = "equivalent_class";
    private Connection connection;

    public EquivalentClassDao() {
        connection = getConnection(DATABASE);
    }

    public int save(EquivalentClass equivalentClass) {
        String sql = "insert into " + TABLE + " values(?, ?)";
        Object[] params = {equivalentClass.getUri1(), equivalentClass.getUri2()};
        return execute(connection, sql, params);
    }

    public static void main(String[] args) {
        EquivalentClassDao dao = new EquivalentClassDao();
        dao.save(new EquivalentClass("qqq", "sss"));
    }
}
