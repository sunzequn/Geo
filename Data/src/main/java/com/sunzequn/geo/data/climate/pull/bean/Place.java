package com.sunzequn.geo.data.climate.pull.bean;

/**
 * Created by Sloriac on 16/1/6.
 */
public class Place {

    private int id;
    private String name;
    private String url;
    private int parentid;
    private int ifvisited;
    private String climate;
    private double temperature;
    private double precipitation;

    public Place() {
    }

    public Place(int id, String name, String url, int parentid, int ifvisited, String climate, double temperature, double precipitation) {
        this.name = name;
        this.url = url;
        this.id = id;
        this.parentid = parentid;
        this.ifvisited = ifvisited;
        this.climate = climate;
        this.temperature = temperature;
        this.precipitation = precipitation;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public int getIfvisited() {
        return ifvisited;
    }

    public void setIfvisited(int ifvisited) {
        this.ifvisited = ifvisited;
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

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }


    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", parentid=" + parentid +
                ", ifvisited=" + ifvisited +
                ", climate='" + climate + '\'' +
                ", temperature=" + temperature +
                ", precipitation=" + precipitation +
                '}';
    }
}
