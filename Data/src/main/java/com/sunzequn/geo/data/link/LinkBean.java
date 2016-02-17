package com.sunzequn.geo.data.link;

/**
 * Created by sloriac on 16-2-17.
 */
public class LinkBean {

    private int geonameid;
    private int climateid;

    public LinkBean() {
    }

    public LinkBean(int geonameid, int climateid) {
        this.geonameid = geonameid;
        this.climateid = climateid;
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
}
