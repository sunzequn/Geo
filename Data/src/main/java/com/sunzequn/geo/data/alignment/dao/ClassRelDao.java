package com.sunzequn.geo.data.alignment.dao;

import com.sunzequn.geo.data.alignment.bean.ClassRel;
import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;

/**
 * Created by Sloriac on 16/2/27.
 */
public class ClassRelDao extends BaseDao {

    private static final String DATABASE = "alignment";
    private static final String TABLE = "class_rel";
    private Connection connection;

    public ClassRelDao() {
        connection = getConnection(DATABASE);
    }

    public int save(ClassRel classRel) {
        String sql = "insert into " + TABLE + " values(?, ?)";
        Object[] params = {classRel.getUri(), classRel.getSuperuri()};
        return execute(connection, sql, params);
    }

    public static void main(String[] args) {
        ClassRelDao dao = new ClassRelDao();
        dao.save(new ClassRel("ddd", "sss"));
    }

}
