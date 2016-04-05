package com.sunzequn.geo.data.baike.bean;

/**
 * Created by sunzequn on 2016/4/5.
 */
public class BasicInfo {

    private String url;
    private String key;
    private String value;

    public BasicInfo() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BasicInfo{" +
                "key='" + key + '\'' +
                ", url='" + url + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
