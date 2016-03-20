package com.sunzequn.geo.data.china.geo;

/**
 * Created by Sloriac on 16/3/9.
 */
public class ChinaCity {

    private int id;
    private String name;
    private String shortname;
    private String mergername;
    private String pinyin;
    private String citycode;
    private String zipcode;
    private int parentid;
    private int leveltype;
    private double lng;
    private double lat;

    public ChinaCity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getMergername() {
        return mergername;
    }

    public void setMergername(String mergername) {
        this.mergername = mergername;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public int getLeveltype() {
        return leveltype;
    }

    public void setLeveltype(int leveltype) {
        this.leveltype = leveltype;
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

    @Override
    public String toString() {
        return "ChinaCity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortname='" + shortname + '\'' +
                ", mergername='" + mergername + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", citycode='" + citycode + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", parentid=" + parentid +
                ", leveltype=" + leveltype +
                ", lng=" + lng +
                ", lat=" + lat +
                '}';
    }
}
