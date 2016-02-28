package com.sunzequn.geo.data.alignment.bean;

import java.util.List;

/**
 * Created by Sloriac on 16/2/28.
 */
public class GeonamesClass implements Comparable<GeonamesClass> {

    //geonames概念代码
    private String uri2;
    //所有的边的权重的和
    private int weight;

    private List<Relation> relatedDbpedias;


    public GeonamesClass() {
    }

    public String getUri2() {
        return uri2;
    }

    public void setUri2(String uri2) {
        this.uri2 = uri2;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Relation> getRelatedDbpedias() {
        return relatedDbpedias;
    }

    public void setRelatedDbpedias(List<Relation> relatedDbpedias) {
        this.relatedDbpedias = relatedDbpedias;
    }

    @Override
    public String toString() {
        return "GeonamesClass{" +
                "uri2='" + uri2 + '\'' +
                ", weight=" + weight +
                ", relatedDbpedias=" + relatedDbpedias +
                '}';
    }

    @Override
    public int compareTo(GeonamesClass o) {
        return o.getWeight() - this.weight;
    }
}
