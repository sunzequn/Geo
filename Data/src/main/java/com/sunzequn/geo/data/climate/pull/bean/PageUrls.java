package com.sunzequn.geo.data.climate.pull.bean;

/**
 * Created by Sloriac on 16/2/11.
 */
public class PageUrls {

    private String url;
    private int ifvisited;

    public PageUrls() {
    }

    public PageUrls(String url, int ifvisited) {
        this.url = url;
        this.ifvisited = ifvisited;
    }

    public int getIfvisited() {
        return ifvisited;
    }

    public void setIfvisited(int ifvisited) {
        this.ifvisited = ifvisited;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PageUrls{" +
                "url='" + url + '\'' +
                ", ifvisited=" + ifvisited +
                '}';
    }
}
