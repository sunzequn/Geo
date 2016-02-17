package com.sunzequn.geo.data.geonames.bean;

/**
 * Created by sloriac on 16-2-17.
 */
public class ContinentCodes {

    private String code;
    private String name;
    private int geonameid;

    public ContinentCodes() {
    }

    public ContinentCodes(String code, String name, int geonameid) {
        this.code = code;
        this.name = name;
        this.geonameid = geonameid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGeonameid() {
        return geonameid;
    }

    public void setGeonameid(int geonameid) {
        this.geonameid = geonameid;
    }
}
