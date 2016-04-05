package com.sunzequn.geo.data.baike.dao;

import com.github.jsonldjava.utils.Obj;
import com.sunzequn.geo.data.baike.bean.TypeLink;
import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/5.
 */
public class TypeLinkDao extends BaseDao {

    private static final String DATABASE = "baidubaike";
    private static final String TABLE = "type_link";
    private Connection connection;

    public TypeLinkDao() {
        connection = getConnection(DATABASE);
    }

    public List<TypeLink> getAll() {
        String sql = "select * from " + TABLE;
        return query(connection, sql, null, TypeLink.class);
    }

    public List<TypeLink> getNarrowByName(String typeName) {
        String sql = "select * from " + TABLE + " where type = ? and relation = ?";
        Object[] params = {typeName, "skos:narrowMatch"};
        return query(connection, sql, params, TypeLink.class);
    }

    public static void main(String[] args) {
        TypeLinkDao dao = new TypeLinkDao();
        System.out.println(dao.getNarrowByName("湖泊"));
    }
}
