package com.sunzequn.geo.data.baike.bean;

/**
 * Created by sunzequn on 2016/4/9.
 */
public class BaikePage {

    private String id;
    private String title;
    private String subTitle;
    private String summary;

    public BaikePage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "BaikePage{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
