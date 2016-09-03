package com.sunzequn.geo.data.baike.bdbk;

import com.sunzequn.geo.data.baike.yuce.TypeKey;
import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/28.
 */
public class TypeUriDao extends BaseDao {

    private static final String DATABASE = "baidubaike";
    private static String TABLE = "url_type_uri";
    private Connection connection;

    public TypeUriDao() {
        connection = getConnection(DATABASE);
    }

    public int add(TypeUri typeUri) {
        String sql = "insert into " + TABLE + " (uri, type) values (?, ?)";
        Object[] parmas = {typeUri.getUri(), typeUri.getType()};
        return execute(connection, sql, parmas);
    }

    public List<TypeUri> getAll() {
        String sql = "select * from " + TABLE;
        return query(connection, sql, null, TypeUri.class);
    }

    public int[] add(List<TypeUri> typeUris) {
        String sql = "insert into " + TABLE + " (uri, type) values (?, ?)";
        Object[][] parmas = new Object[typeUris.size()][2];
        for (int i = 0; i < typeUris.size(); i++) {
            TypeUri typeUri = typeUris.get(i);
            parmas[i][0] = typeUri.getUri();
            parmas[i][1] = typeUri.getType();
        }
        return batch(connection, sql, parmas);
    }
}
