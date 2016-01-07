package com.sunzequn.geo.data.crawler.application.climate.pull.bean;

/**
 * Created by Sloriac on 16/1/6.
 */
public class Region {

    private int id;
    private String name;
    private String url;
    private int parentid;
    private int ifvisited;

    public Region() {
    }

    public Region(int id, String name, String url, int parentid, int ifvisited) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.parentid = parentid;
        this.ifvisited = ifvisited;
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

    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", parentid=" + parentid +
                ", ifvisited=" + ifvisited +
                '}';
    }
}
