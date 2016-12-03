package com.sunzequn.geo.data.geonamesplus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.LinkedList;

/**
 * 
 * @author sunzequn
 *
 */
public class ConnectionInvocationHandler implements InvocationHandler {

    private Connection proxy;
    private LinkedList<Connection> connections;

    public ConnectionInvocationHandler(Connection proxy, LinkedList<Connection> connections) {
        this.proxy = proxy;
        this.connections = connections;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //When calling the method "connection.close()", put the connection back to the pool.
        if (method.getName().equals("close")) {
            connections.push((Connection) this.proxy);
            return null;
        }
        return method.invoke(this.proxy, args);
    }
}
