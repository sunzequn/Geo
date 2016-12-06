package com.sunzequn.geo.data.geonamesplus.handler;

/**
 * Created by sloriac on 16-12-5.
 */
public class NamesMapping {

    private int geonameid;
    private String name;

    public NamesMapping(int geonameid, String name) {
        this.geonameid = geonameid;
        this.name = name;
    }

    public int getGeonameid() {
        return geonameid;
    }

    public void setGeonameid(int geonameid) {
        this.geonameid = geonameid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
