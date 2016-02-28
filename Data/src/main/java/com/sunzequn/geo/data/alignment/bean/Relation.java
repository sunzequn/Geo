package com.sunzequn.geo.data.alignment.bean;

/**
 * Created by Sloriac on 16/2/28.
 */
public class Relation implements Comparable<Relation> {

    private String uri;
    //相对的那条边的权重
    private int weight;
    //这个uri的全部边的权重和
    private int allWeight;

    public Relation() {
    }

    public Relation(String uri, int weight, int allweight) {
        this.uri = uri;
        this.weight = weight;
        this.allWeight = allweight;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getAllWeight() {
        return allWeight;
    }

    public void setAllWeight(int allWeight) {
        this.allWeight = allWeight;
    }

    @Override
    public String toString() {
        return "Relation{" +
                "uri='" + uri + '\'' +
                ", weight=" + weight +
                ", allWeight=" + allWeight +
                '}';
    }

    @Override
    public int compareTo(Relation o) {
        return o.getWeight() / o.getAllWeight() - this.weight / this.allWeight;
    }
}
