package com.sunzequn.geo.data.baike.bdbk;

/**
 * Created by sunzequn on 2016/4/11.
 */
public class Summary {

    private String url;
    private String summary;

    public Summary() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "Summary{" +
                "url='" + url + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
