package com.sunzequn.geo.data.crawler.application.climate.pull.bean;

/**
 * Created by Sloriac on 16/1/6.
 */
public class Continent {

    private int id;
    private String name;
    private String url;

    public Continent() {
    }

    public Continent(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Continent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
