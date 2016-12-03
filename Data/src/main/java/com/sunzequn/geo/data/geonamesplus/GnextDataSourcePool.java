package com.sunzequn.geo.data.geonamesplus;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * 
 * @author sunzequn
 *
 */
public class GnextDataSourcePool implements javax.sql.DataSource{
	
	private static class LazyHolder {    
	       private static final GnextDataSourcePool INSTANCE = new GnextDataSourcePool();    
	    }    
    
    private GnextDataSource dataSource;

    private LinkedList<Connection> connections;
    
    public static void initialize(){
    	instance();
    }

    public static GnextDataSourcePool instance() {
        return LazyHolder.INSTANCE;
    }

    private GnextDataSourcePool() {
        super();
        dataSource = new GnextDataSource();
        connections = new LinkedList<>();
        for (int i = 0; i < dataSource.getInitialPoolSize(); i++) {
            connections.add(new GnextDataSource().getConnection());
        }
    }
    
    public int getNumOfConnections() {
		return connections.size();
	}

    @Override
    public Connection getConnection() {
        if (connections.size() > 0) {
            final Connection connection = connections.pop();
            ConnectionInvocationHandler connHandler = new ConnectionInvocationHandler(connection, connections);
            return ProxyFactory.instance().createConnection(connHandler);
        } else {
        	//
            return null;
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

}
