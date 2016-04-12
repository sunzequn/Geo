package com.sunzequn.geo.data.dao;

import com.sunzequn.geo.data.geonames.dao.ContinentCodesDao;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Sloriac on 16/1/6.
 */
public class BaseDao {

    private static final String CLASS_NAME = "com.mysql.jdbc.Driver";
    //    private static final String JDBC_URL_PREFIX = "jdbc:mysql://localhost:3306/";
    private static final String JDBC_URL_PREFIX = "jdbc:mysql://114.212.83.214:3306/";
    private static final String JDBC_URL_SUFFIX = "?useUnicode=true";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    protected Connection getConnection(String databaseName) {
        try {
            Class.forName(CLASS_NAME);
            return DriverManager.getConnection(JDBC_URL_PREFIX + databaseName + JDBC_URL_SUFFIX, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Connection getConnection(String databaseName, String user, String password) {
        try {
            Class.forName(CLASS_NAME);
            return DriverManager.getConnection(JDBC_URL_PREFIX + databaseName + JDBC_URL_SUFFIX, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected <T> List<T> query(Connection connection, String sql, Object[] params, Class clazz) {
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

    protected int execute(Connection connection, String sql, Object[] params) {
        QueryRunner queryRunner = new QueryRunner();
        try {
            int res = queryRunner.update(connection, sql, params);
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    protected int[] batch(Connection connection, String sql, Object[][] params) {
        try {
            connection.setAutoCommit(false);
            QueryRunner queryRunner = new QueryRunner();
            int[] res = queryRunner.batch(connection, sql, params);
            connection.commit();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
