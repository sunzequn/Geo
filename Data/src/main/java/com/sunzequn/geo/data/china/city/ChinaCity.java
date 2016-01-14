package com.sunzequn.geo.data.china.city;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.zip.DeflaterOutputStream;

/**
 * Created by Sloriac on 15/12/30.
 */
public class ChinaCity {

    private int id;
    private String name;
    private String shortName;
    private String mergerName;
    private String pinyin;
    private String cityCode;
    private String zipCode;
    private int parentId;
    private int levelType;
    private double lng;
    private double lat;

    public ChinaCity() {
    }

    public ChinaCity(List<String> columns) {
        lineToCity(columns);
    }

    public void lineToCity(List<String> columns) {
        int length = columns.size();
        id = Integer.parseInt(columns.get(0));
        name = columns.get(1);
        parentId = Integer.parseInt(columns.get(2));
        if (length == 7) {
            levelType = Integer.parseInt(columns.get(3));
            mergerName = columns.get(4);
            lng = Double.parseDouble(columns.get(5));
            lat = Double.parseDouble(columns.get(6));
        } else {
            shortName = columns.get(3);
            levelType = Integer.parseInt(columns.get(4));
            mergerName = columns.get(length - 4);
            lng = Double.parseDouble(columns.get(length - 3));
            lat = Double.parseDouble(columns.get(length - 2));
            pinyin = columns.get(length - 1);
            if (length == 11) {
                cityCode = columns.get(5);
                zipCode = columns.get(6);
            }
        }

        System.out.println(toString());
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getMergerName() {
        return mergerName;
    }

    public void setMergerName(String mergerName) {
        this.mergerName = mergerName;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getLevelType() {
        return levelType;
    }

    public void setLevelType(int levelType) {
        this.levelType = levelType;
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
                ", shortName='" + shortName + '\'' +
                ", mergerName='" + mergerName + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", parentId=" + parentId +
                ", levelType=" + levelType +
                ", lng=" + lng +
                ", lat=" + lat +
                '}';
    }
}
