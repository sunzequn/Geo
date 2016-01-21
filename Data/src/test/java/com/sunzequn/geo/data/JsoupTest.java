package com.sunzequn.geo.data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

/**
 * Created by Sloriac on 16/1/20.
 */
public class JsoupTest {

    public static void main(String[] args) throws IOException {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("183.130.91.203", 9000));

        URL url = new URL("http://sws.geonames.org/3020251/nearby.rdf");
        HttpURLConnection action = (HttpURLConnection) url.openConnection(proxy);
        action.setConnectTimeout(1000);
//        action.setReadTimeout(1000);
        InputStream in = action.getInputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String lin = System.getProperty("line.separator");
        for (String temp = br.readLine(); temp != null; temp = br.readLine()) {

            sb.append(temp + lin);

        }

        br.close();
        in.close();
        System.out.println(sb);
    }
}
