package com.sunzequn.geo.data.alignment.dao;

import com.sunzequn.geo.data.alignment.bean.ClassLink;
import com.sunzequn.geo.data.alignment.bean.ClassRel;
import com.sunzequn.geo.data.alignment.bean.EquivalentClass;
import com.sunzequn.geo.data.dao.BaseDao;
import com.sunzequn.geo.data.utils.ListUtils;

import java.sql.Connection;
import java.util.List;

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
        if (getById(classLink.getUri1(), classLink.getUri2()) == null) {
            String sql = "insert into " + TABLE + " values(?, ?, ?)";
            Object[] params = {classLink.getUri1(), classLink.getUri2(), classLink.getWeight()};
            return execute(connection, sql, params);
        } else {
            String sql = "update " + TABLE + " set weight = weight + 1 where uri1 = ? and uri2 = ?";
            Object[] params = {classLink.getUri1(), classLink.getUri2()};
            return execute(connection, sql, params);
        }
    }

    public ClassLink getById(String uri1, String uri2) {
        String sql = "select * from " + TABLE + " where uri1 = ? and uri2 = ?";
        Object[] params = {uri1, uri2};
        List<ClassLink> classLinks = query(connection, sql, params, ClassLink.class);
        if (ListUtils.isEmpty(classLinks))
            return null;
        return classLinks.get(0);
    }

    public static void main(String[] args) {
        ClassLinkDao dao = new ClassLinkDao();
        dao.save(new ClassLink("qqq", "sss", 1));
    }
}
