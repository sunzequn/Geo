package com.sunzequn.geo.data.geonamesplus;

/**
 * 
 * @author sunzequn
 *
 */
public class Koppen {

    private double longitude;
    private double latitude;
    private String type;

    public Koppen() {
    }

    public Koppen(double longitude, double latitude, String type) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Koppen{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", type='" + type + '\'' +
                '}';
    }
}
