package com.sunzequn.geo.data.geonames.cn;

/**
 * Created by sunzequn on 2016/6/27.
 */
public class Geonamecn {

    private int geonameid;
    private String zhname;

    public Geonamecn(int geonameid, String zhname) {
        this.geonameid = geonameid;
        this.zhname = zhname;
    }

    public int getGeonameid() {
        return geonameid;
    }

    public void setGeonameid(int geonameid) {
        this.geonameid = geonameid;
    }

    public String getZhname() {
        return zhname;
    }

    public void setZhname(String zhname) {
        this.zhname = zhname;
    }

    @Override
    public String toString() {
        return "Geonamecn{" +
                "geonameid=" + geonameid +
                ", zhname='" + zhname + '\'' +
                '}';
    }
}
