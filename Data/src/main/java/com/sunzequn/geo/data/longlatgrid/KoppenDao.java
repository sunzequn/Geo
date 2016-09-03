package com.sunzequn.geo.data.longlatgrid;

import com.sunzequn.geo.data.dao.BaseDao;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sunzequn on 2016/6/29.
 */
public class KoppenDao extends BaseDao {

    private static final String DATABASE = "climate";
    private static final String TABLE = "koppen";
    private Connection connection;

    public KoppenDao() {
        connection = getConnection(DATABASE);
    }

    public List<Koppen> getAll() {
        String sql = "SELECT * FROM " + TABLE + " ORDER BY latitude DESC, longitude";
        return query(connection, sql, null, Koppen.class);
    }

    public static void main(String[] args) {
        KoppenDao dao = new KoppenDao();
        System.out.println(dao.getAll().size());
    }
}
