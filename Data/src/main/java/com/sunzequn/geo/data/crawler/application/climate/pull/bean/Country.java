package com.sunzequn.geo.data.crawler.application.climate.pull.bean;

/**
 * Created by Sloriac on 16/1/6.
 */
public class Country {

    private int id;
    private String name;
    private String url;
    private int parentid;

    public Country() {
    }

    public Country(int id, String name, String url, int parentid) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.parentid = parentid;
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

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", parentid=" + parentid +
                '}';
    }
}
