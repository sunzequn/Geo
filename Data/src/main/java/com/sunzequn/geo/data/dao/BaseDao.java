package com.sunzequn.geo.data.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Sloriac on 16/1/6.
 */
public abstract class BaseDao {

    private static final String CLASS_NAME = "com.mysql.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/geocities?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static Connection connection;

    protected void getConnection() {
        try {
            Class.forName(CLASS_NAME);
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected <T> List<T> query(String sql, Object[] params, Class clazz) {
        QueryRunner queryRunner = new QueryRunner();
        try {
            List<T> ts = queryRunner.query(connection, sql, new BeanListHandler<T>(clazz), params);
            if (ts != null && ts.size() > 0) {
                return ts;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
