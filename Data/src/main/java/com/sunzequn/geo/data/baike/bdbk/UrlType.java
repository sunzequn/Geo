package com.sunzequn.geo.data.baike.bdbk;

/**
 * Created by sunzequn on 2016/4/11.
 */
public class UrlType {

    private String url;
    private String typr;
    private int confidence;
    private String title;
    private String subtitle;
    private String summary;

    public UrlType() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTypr() {
        return typr;
    }

    public void setTypr(String typr) {
        this.typr = typr;
    }

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "UrlType{" +
                "url='" + url + '\'' +
                ", typr='" + typr + '\'' +
                ", confidence=" + confidence +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
