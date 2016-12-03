package com.sunzequn.geo.data.geonamesplus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * 
 * @author sunzequn
 *
 */
public abstract class BaseDao {
	
	public <T> List<T> query(Connection connection, String sql, Object[] params, Class clazz) {
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

	public int execute(Connection connection, String sql, Object[] params) {
        QueryRunner queryRunner = new QueryRunner();
        try {
            return queryRunner.update(connection, sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

	public void close(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
