package com.sunzequn.geo.data.baike.bdbk;

/**
 * Created by sunzequn on 2016/4/18.
 */
public class Tag {

    private String url;
    private String open_tag;

    public Tag() {
    }

    public Tag(String url, String open_tag) {
        this.url = url;
        this.open_tag = open_tag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOpen_tag() {
        return open_tag;
    }

    public void setOpen_tag(String open_tag) {
        this.open_tag = open_tag;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "url='" + url + '\'' +
                ", open_tag='" + open_tag + '\'' +
                '}';
    }
}
