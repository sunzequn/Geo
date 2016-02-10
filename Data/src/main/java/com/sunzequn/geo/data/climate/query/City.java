package com.sunzequn.geo.data.climate.query;

/**
 * Created by Sloriac on 15/12/20.
 */
public class City {

    private String city;
    private String province;
    private String country;

    public City() {
    }

    public City(String city, String province, String country) {
        this.city = city;
        this.country = country;
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return city + "," +
                province + "," +
                country;
    }
}
