package com.sunzequn.geo.data.baike.bean;

/**
 * Created by Sloriac on 16/3/15.
 */
public class InfoBox {

    private String uri;
    private String title;
    private String comment;
    private String prop;
    private String value;

    public InfoBox() {
    }

    public InfoBox(String uri, String title, String comment, String prop, String value) {
        this.uri = uri;
        this.title = title;
        this.comment = comment;
        this.prop = prop;
        this.value = value;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "InfoBox{" +
                "uri='" + uri + '\'' +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", prop='" + prop + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
