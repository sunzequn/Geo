package com.sunzequn.geo.data.baike.bdbk;

/**
 * Created by sunzequn on 2016/4/11.
 */
public class UrlType {

    private String url;
    private String type;
    private int confidence;
    private String title;
    private String subtitle;
    private String summary;

    public UrlType() {
    }

    public UrlType(String url, String title, String subtitle, String summary) {
        this.url = url;
        this.title = title;
        this.subtitle = subtitle;
        this.summary = summary;
    }

    public UrlType(String url, String type, int confidence) {
        this.url = url;
        this.type = type;
        this.confidence = confidence;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
                ", type='" + type + '\'' +
                ", confidence=" + confidence +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
