package com.sunzequn.geo.data.baike.bean;

import com.sunzequn.geo.data.baike.bdbk.SubTitle;
import com.sunzequn.geo.data.baike.bdbk.Title;
import org.neo4j.cypher.internal.compiler.v2_2.functions.Str;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/9.
 */
public class BaikePage {

    private String url;
    private String title;
    private String subTitle;
    private String summary;
    private HashMap<String, String> basicInfos;

    public BaikePage() {
    }

//    public BaikePage(Title title) {
//        this.url = title.getUrl();
//        this.title = title.getTitle();
//    }
//
//    public BaikePage(SubTitle subTitle){
//        this.url = subTitle.getUrl();
//        this.subTitle = subTitle.getSubtitle();
//    }
//
//    public BaikePage(List<BasicIn>){
//
//    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

}
