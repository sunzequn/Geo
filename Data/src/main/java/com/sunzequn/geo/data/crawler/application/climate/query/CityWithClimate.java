package com.sunzequn.geo.data.crawler.application.climate.query;

import java.math.BigDecimal;

/**
 * Created by Sloriac on 15/12/20.
 */
public class CityWithClimate {

    private String city;
    private String province;
    private String country;
    private String climate;
    private double temperature;
    private int precipitation;

    public CityWithClimate() {
    }

    public CityWithClimate(City city) {
        setCity(city.getCity());
        setProvince(city.getProvince());
        setCountry(city.getCountry());
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        double d = Double.parseDouble(temperature);
        BigDecimal b = new BigDecimal(d);
        double d1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        this.temperature = d1;
    }

    public int getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = Integer.parseInt(precipitation);
    }

    @Override
    public String toString() {
        return city + "," +
                province + "," +
                country + "," +
                climate + "," +
                temperature + "," +
                precipitation;
    }
}
