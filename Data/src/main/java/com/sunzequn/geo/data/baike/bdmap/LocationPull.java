package com.sunzequn.geo.data.baike.bdmap;

import com.google.gson.Gson;
import com.sunzequn.geo.data.crawler.parser.HttpMethod;
import com.sunzequn.geo.data.crawler.parser.PullText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Sloriac on 16/3/20.
 */
public class LocationPull extends PullText {


    private static String prefix = "http://api.map.baidu.com/geocoder/v2/?address=";
    private static String suffix = "&output=json&ak=F12acc6f0e6b3d447be9e226846e4f6e";

    public static void main(String[] args) throws IOException {
        LocationPull locationHandler = new LocationPull();
        locationHandler.handler();
    }

    public BDDT getLngLat(String location) {
        String url = prefix + location.trim() + suffix;
        String res = pullFromUrl(url, 5000, HttpMethod.Get).text();
        Gson gson = new Gson();
        return gson.fromJson(res, BDDT.class);
    }

    public void handler() throws IOException {
        BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = strin.readLine();
            if (line.equals("end")) {
                return;
            }
            System.out.println(getLngLat(line));
        }
    }

}
