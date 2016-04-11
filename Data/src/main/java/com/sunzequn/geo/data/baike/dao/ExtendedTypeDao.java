package com.sunzequn.geo.data.baike.dao;

import com.sunzequn.geo.data.baike.bean.ExtendedType;
import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/5.
 */
public class ExtendedTypeDao extends BaseDao {

    private static final String DATABASE = "baidubaike";
    private static final String TABLE = "extended_type";
    private Connection connection;

    public ExtendedTypeDao() {
        connection = getConnection(DATABASE);
    }

    public List<ExtendedType> getAll() {
        String sql = "select * from " + TABLE;
        return query(connection, sql, null, ExtendedType.class);
    }

    public static void main(String[] args) {
        ExtendedTypeDao dao = new ExtendedTypeDao();
        System.out.println(dao.getAll());
    }
}
