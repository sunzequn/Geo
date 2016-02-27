package com.sunzequn.geo.data.alignment.dao;

import com.sunzequn.geo.data.alignment.bean.TypeRel;
import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;

/**
 * Created by Sloriac on 16/2/27.
 */
public class TypeRelDao extends BaseDao {

    private static final String DATABASE = "alignment";
    private static final String TABLE = "type_rel";
    private Connection connection;

    public TypeRelDao() {
        connection = getConnection(DATABASE);
    }

    public int save(TypeRel typeRel) {
        String sql = "insert into " + TABLE + " values(?, ?)";
        Object[] params = {typeRel.getUri(), typeRel.getSuperuri()};
        return execute(connection, sql, params);
    }

    public static void main(String[] args) {
        TypeRelDao dao = new TypeRelDao();
        dao.save(new TypeRel("ddd", "sss"));
    }

}
