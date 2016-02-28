package com.sunzequn.geo.data.alignment.dao;

import com.sunzequn.geo.data.alignment.bean.ClassLink;
import com.sunzequn.geo.data.alignment.bean.EquivalentClass;
import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;

/**
 * Created by Sloriac on 16/2/27.
 */
public class ClassLinkDao extends BaseDao {

    private static final String DATABASE = "alignment";
    private static final String TABLE = "class_link";
    private Connection connection;

    public ClassLinkDao() {
        connection = getConnection(DATABASE);
    }

    public int save(ClassLink classLink) {
        String sql = "insert into " + TABLE + " values(?, ?, ?)";
        Object[] params = {classLink.getUri1(), classLink.getUri2(), classLink.getWeight()};
        return execute(connection, sql, params);
    }

    public static void main(String[] args) {
        ClassLinkDao dao = new ClassLinkDao();
        dao.save(new ClassLink("qqq", "sss", 1));
    }
}
