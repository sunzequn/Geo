package com.sunzequn.geo.data.geonames.crawler;

import com.sunzequn.geo.data.exception.HttpException;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;

/**
 * Created by Sloriac on 16/1/21.
 */
public class HttpConnector {

    private URI uri = null;
    private HttpURLConnection connection = null;

    public HttpConnector setProxy(String hostname, int port) throws HttpException {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(hostname, port));
        if (connection == null) {
            throw new HttpException("应先设置url再设置代理");
        }
        return this;
    }
}
