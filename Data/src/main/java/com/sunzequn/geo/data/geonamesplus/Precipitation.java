package com.sunzequn.geo.data.geonamesplus;

/**
 * 
 * @author sunzequn
 *
 */
public class Precipitation {

    private double longitude;
    private double latitude;
    private int year;
    private int month;
    private double precipitation;

    public Precipitation() {
    }

    public Precipitation(double longitude, double latitude, int year, int month, double precipitation) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.year = year;
        this.month = month;
        this.precipitation = precipitation;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    @Override
    public String toString() {
        return "Precipitation{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", year=" + year +
                ", month=" + month +
                ", precipitation=" + precipitation +
                '}';
    }
}
