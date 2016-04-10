package com.sunzequn.geo.data.baike.bdbk;

import org.neo4j.cypher.internal.compiler.v2_2.functions.Str;

/**
 * Created by sunzequn on 2016/4/10.
 */
public class Title implements BDBK {
    private String url;
    private String title;

    public Title() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Title{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
