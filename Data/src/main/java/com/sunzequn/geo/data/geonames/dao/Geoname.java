package com.sunzequn.geo.data.geonames.dao;

import java.util.Date;

/**
 * Created by sloriac on 16-2-14.
 */
public class Geoname {

    private int geonameid;
    private String name;
    private String asciiname;
    private String alternatenames;
    private double latitude;
    private double longitude;
    private String fclass;
    private String fcode;
    private String country;
    private String cc2;
    private String admin1;
    private String admin2;
    private String admin3;
    private String admin4;
    private int population;
    private int elevation;
    private int gtopo30;
    private String timezone;
    private Date moddate;

    public Geoname() {
    }

    public Geoname(int geonameid) {
        this.geonameid = geonameid;
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

    public String getAsciiname() {
        return asciiname;
    }

    public void setAsciiname(String asciiname) {
        this.asciiname = asciiname;
    }

    public String getAlternatenames() {
        return alternatenames;
    }

    public void setAlternatenames(String alternatenames) {
        this.alternatenames = alternatenames;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getFclass() {
        return fclass;
    }

    public void setFclass(String fclass) {
        this.fclass = fclass;
    }

    public String getFcode() {
        return fcode;
    }

    public void setFcode(String fcode) {
        this.fcode = fcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCc2() {
        return cc2;
    }

    public void setCc2(String cc2) {
        this.cc2 = cc2;
    }

    public String getAdmin1() {
        return admin1;
    }

    public void setAdmin1(String admin1) {
        this.admin1 = admin1;
    }

    public String getAdmin2() {
        return admin2;
    }

    public void setAdmin2(String admin2) {
        this.admin2 = admin2;
    }

    public String getAdmin3() {
        return admin3;
    }

    public void setAdmin3(String admin3) {
        this.admin3 = admin3;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getAdmin4() {
        return admin4;
    }

    public void setAdmin4(String admin4) {
        this.admin4 = admin4;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public int getGtopo30() {
        return gtopo30;
    }

    public void setGtopo30(int gtopo30) {
        this.gtopo30 = gtopo30;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Date getModdate() {
        return moddate;
    }

    public void setModdate(Date moddate) {
        this.moddate = moddate;
    }

    @Override
    public String toString() {
        return "Geoname{" +
                "geonameid=" + geonameid +
                ", name='" + name + '\'' +
                ", asciiname='" + asciiname + '\'' +
                ", alternatenames='" + alternatenames + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", fclass='" + fclass + '\'' +
                ", fcode='" + fcode + '\'' +
                ", country='" + country + '\'' +
                ", cc2='" + cc2 + '\'' +
                ", admin1='" + admin1 + '\'' +
                ", admin2='" + admin2 + '\'' +
                ", admin3='" + admin3 + '\'' +
                ", admin4='" + admin4 + '\'' +
                ", population=" + population +
                ", elevation=" + elevation +
                ", gtopo30=" + gtopo30 +
                ", timezone='" + timezone + '\'' +
                ", moddate=" + moddate +
                '}';
    }
}
