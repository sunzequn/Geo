package com.sunzequn.geo.data.baike.bdbk;

/**
 * Created by sunzequn on 2016/4/10.
 */
public class SubTitle implements BDBK {
    private String url;
    private String subtitle;

    public SubTitle() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @Override
    public String toString() {
        return "Title{" +
                "url='" + url + '\'' +
                ", subtitle='" + subtitle + '\'' +
                '}';
    }
}
