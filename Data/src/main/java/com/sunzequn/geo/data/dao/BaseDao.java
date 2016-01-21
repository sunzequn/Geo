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
    private static final String JDBC_URL_PREFIX = "jdbc:mysql://localhost:3306/";
    private static final String JDBC_URL_SUFFIX = "?useUnicode=true&characterEncoding=UTF-8";
    private static final String DATABASE = "geocities";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    protected static Connection connection;

    protected void getConnection() {
        try {
            Class.forName(CLASS_NAME);
            connection = DriverManager.getConnection(JDBC_URL_PREFIX + DATABASE + JDBC_URL_SUFFIX, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void getConnection(String databaseName) {
        try {
            Class.forName(CLASS_NAME);
            connection = DriverManager.getConnection(JDBC_URL_PREFIX + databaseName + JDBC_URL_SUFFIX, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void getConnection(String databaseName, String user, String password) {
        try {
            Class.forName(CLASS_NAME);
            connection = DriverManager.getConnection(JDBC_URL_PREFIX + databaseName + JDBC_URL_SUFFIX, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
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

    protected int execute(String sql, Object[] params) {
        QueryRunner queryRunner = new QueryRunner();
        try {
            int res = queryRunner.update(connection, sql, params);
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
