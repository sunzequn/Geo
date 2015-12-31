package com.sunzequn.geocities.data.china.geonames;

/**
 * Created by Sloriac on 15/12/31.
 */
public class GeoCity {

    private int geonamesid;
    private String name;
    private int parentid;

    public GeoCity() {
    }

    public GeoCity(int geonamesid, String name, int parentid) {
        this.geonamesid = geonamesid;
        this.name = name;
        this.parentid = parentid;
    }

    public int getGeonamesid() {
        return geonamesid;
    }

    public void setGeonamesid(int geonamesid) {
        this.geonamesid = geonamesid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }
}
