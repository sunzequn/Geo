package com.sunzequn.geo.data.baike.bdbk;

/**
 * Created by sunzequn on 2016/4/14.
 */
public class Catalog {

    private String url;
    private String catalog_item;

    public Catalog() {
    }

    public Catalog(String url, String catalog_item) {
        this.url = url;
        this.catalog_item = catalog_item;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCatalog_item() {
        return catalog_item;
    }

    public void setCatalog_item(String catalog_item) {
        this.catalog_item = catalog_item;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "url='" + url + '\'' +
                ", catalog_item='" + catalog_item + '\'' +
                '}';
    }
}
