package com.sunzequn.geo.data.gpcp;

/**
 * Created by sunzequn on 2016/6/26.
 */
public class LngLat {

    private double lng;
    private double lat;

    public LngLat() {
    }

    public LngLat(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
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

    @Override
    public String toString() {
        return "LngLat{" +
                "lng=" + lng +
                ", lat=" + lat +
                '}';
    }
}
