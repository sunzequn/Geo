package com.sunzequn.geo.data.baike.count;

/**
 * Created by sunzequn on 2016/4/15.
 */
public class GeonamesLink {

    private String url;
    private String link;
    private String type;

    public GeonamesLink() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "GeonamesLink{" +
                "url='" + url + '\'' +
                ", link='" + link + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
