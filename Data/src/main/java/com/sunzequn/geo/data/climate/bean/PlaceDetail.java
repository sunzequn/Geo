package com.sunzequn.geo.data.climate.bean;

/**
 * Created by Sloriac on 16/3/3.
 */
public class PlaceDetail {

    private String url;
    private String detail;

    public PlaceDetail() {
    }

    public PlaceDetail(String url, String detail) {
        this.url = url;
        this.detail = detail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


    @Override
    public String toString() {
        return "PlaceDetail{" +
                "url='" + url + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
