package com.sunzequn.geo.data.geonamesplus;

import java.sql.Connection;
import java.util.List;

/**
 * 
 * @author sunzequn
 *
 */
public class GnextBaseDao extends BaseDao {
	
	private GnextDataSourcePool gnextDataSourcePool = GnextDataSourcePool.instance();
	
	public <T> List<T> query(String sql, Object[] params, Class clazz) {
        Connection connection = gnextDataSourcePool.getConnection();
        List<T> ts = query(connection, sql, params, clazz);
        close(connection);
        return ts;
    }

}
