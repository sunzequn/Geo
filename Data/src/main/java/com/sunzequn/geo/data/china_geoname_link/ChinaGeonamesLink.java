package com.sunzequn.geo.data.china_geoname_link;

/**
 * Created by sunzequn on 2016/6/28.
 */
public class ChinaGeonamesLink {

    private int cityid;
    private int geonameid;
    private String leveltype;
    private double confidence;

    public ChinaGeonamesLink() {
    }

    public ChinaGeonamesLink(int cityid, int geonameid, String leveltype, double confidence) {
        this.cityid = cityid;
        this.geonameid = geonameid;
        this.leveltype = leveltype;
        this.confidence = confidence;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public int getGeonameid() {
        return geonameid;
    }

    public void setGeonameid(int geonameid) {
        this.geonameid = geonameid;
    }

    public String getLeveltype() {
        return leveltype;
    }

    public void setLeveltype(String leveltype) {
        this.leveltype = leveltype;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "ChinaGeonamesLink{" +
                "cityid=" + cityid +
                ", geonameid=" + geonameid +
                ", leveltype='" + leveltype + '\'' +
                ", confidence=" + confidence +
                '}';
    }
}
