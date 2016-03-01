package com.sunzequn.geo.data.alignment.bean;

import java.util.List;

/**
 * Created by Sloriac on 16/2/28.
 */
public class DbpediaClass implements Comparable<DbpediaClass> {

    //简写的dbpedia类的Uri
    private String uri1;
    //该类所有的直接link的边的权重的和
    private int weight;

    private List<Relation> relatedGeonames;

    public DbpediaClass() {
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getUri1() {
        return uri1;
    }

    public void setUri1(String uri1) {
        this.uri1 = uri1;
    }

    public List<Relation> getRelatedGeonames() {
        return relatedGeonames;
    }

    public void setRelatedGeonames(List<Relation> relatedGeonames) {
        this.relatedGeonames = relatedGeonames;
    }


    @Override
    public String toString() {
        return "DbpediaClass{" +
                "uri1='" + uri1 + '\'' +
                ", weight=" + weight +
                ", relatedGeonames=" + relatedGeonames +
                '}';
    }

    @Override
    public int compareTo(DbpediaClass o) {
        return o.getWeight() - this.weight;
    }
}
