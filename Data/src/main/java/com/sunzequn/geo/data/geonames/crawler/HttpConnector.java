package com.sunzequn.geo.data.geonames.crawler;

import com.sunzequn.geo.data.exception.HttpException;
import com.sunzequn.geo.data.utils.TimeUtils;

import java.io.*;
import java.net.*;

/**
 * Created by Sloriac on 16/1/21.
 */
public class HttpConnector {

    private URL url = null;
    private HttpURLConnection connection = null;
    private Proxy proxy = null;

    public HttpConnector setUrl(String uri) throws MalformedURLException {
        url = new URL(uri);
        return this;
    }

    public HttpConnector setProxy(String hostname, int port) throws HttpException {
        if (connection != null) {
            throw new HttpException("在getConnection()之后设置代理是无效的");
        } else {
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(hostname, port));
        }
        return this;
    }

    public HttpConnector setTimeout(int timeout) throws HttpException {
        if (connection == null) {
            throw new HttpException("应该在getConnection之后设置超时时间");
        } else {
            connection.setConnectTimeout(timeout);
        }
        return this;
    }

    public HttpConnector getConnection() throws IOException, HttpException {
        if (url == null) {
            throw new HttpException("应先设置url再进行连接");
        }
        if (proxy == null) {
            connection = (HttpURLConnection) url.openConnection();
        } else {
            connection = (HttpURLConnection) url.openConnection(proxy);
        }
        return this;
    }

    public String getContent() throws Exception {
        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder stringBuilder = new StringBuilder();
        String line = System.getProperty("line.separator");
        for (String temp = bufferedReader.readLine(); temp != null; temp = bufferedReader.readLine()) {
            stringBuilder.append(temp + line);
        }
        bufferedReader.close();
        inputStream.close();
        return stringBuilder.toString();
    }

}
