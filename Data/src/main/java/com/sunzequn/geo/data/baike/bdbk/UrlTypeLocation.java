package com.sunzequn.geo.data.baike.bdbk;

/**
 * Created by sunzequn on 2016/4/12.
 */
public class UrlTypeLocation {

    private String url;
    private String type;
    private String title;
    private double lng;
    private double lat;
    private int precise;
    private int confidence;
    private String level;

    public UrlTypeLocation() {
    }

    public UrlTypeLocation(String url, String type, String title) {
        this.url = url;
        this.type = type;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getPrecise() {
        return precise;
    }

    public void setPrecise(int precise) {
        this.precise = precise;
    }

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "UrlTypeLocation{" +
                "url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", lng=" + lng +
                ", lat=" + lat +
                ", precise=" + precise +
                ", confidence=" + confidence +
                ", level='" + level + '\'' +
                '}';
    }
}
