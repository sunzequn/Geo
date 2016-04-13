package com.sunzequn.geo.data.baike.bdmap;

/**
 * Created by sunzequn on 2016/4/12.
 */
public class Location {

    private double lng;
    private double lat;

    public Location() {
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
        return "Location{" +
                "lng=" + lng +
                ", lat=" + lat +
                '}';
    }
}
