package com.sunzequn.geo.data.longlatgrid;

/**
 * Created by sunzequn on 2016/6/29.
 */
public class Grid {

    private double longitude;
    private double latitude;

    public Grid(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Grid{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
