package com.sunzequn.geo.data.baike.bdbk;

/**
 * Created by sunzequn on 2016/4/27.
 */
public class Jingweidu {

    private String url;
    private double lng;
    private double lat;

    public Jingweidu() {
    }

    public Jingweidu(String url, double lng, double lat) {
        this.url = url;
        this.lng = lng;
        this.lat = lat;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
