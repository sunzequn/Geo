package com.sunzequn.geo.data.link;

import java.util.Comparator;

/**
 * Created by sloriac on 16-2-17.
 */
public class LinkBean implements Comparable {

    private int geonameid;
    private int climateid;
    private double confidence;

    public LinkBean() {
    }

    public LinkBean(int geonameid, int climateid, double confidence) {
        this.geonameid = geonameid;
        this.climateid = climateid;
        this.confidence = confidence;
    }

    public int getClimateid() {
        return climateid;
    }

    public void setClimateid(int climateid) {
        this.climateid = climateid;
    }

    public int getGeonameid() {
        return geonameid;
    }

    public void setGeonameid(int geonameid) {
        this.geonameid = geonameid;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "LinkBean{" +
                "geonameid=" + geonameid +
                ", climateid=" + climateid +
                ", confidence=" + confidence +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        LinkBean linkBean = (LinkBean) o;
        double diff = linkBean.getConfidence() - this.getConfidence();
        if (diff > 0) {
            return 1;
        } else if (diff < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
