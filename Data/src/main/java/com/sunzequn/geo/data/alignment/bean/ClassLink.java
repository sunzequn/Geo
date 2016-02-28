package com.sunzequn.geo.data.alignment.bean;

/**
 * Created by Sloriac on 16/2/27.
 */
public class ClassLink {
    //dbpedia的class
    private String uri1;
    //geonames的class
    private String uri2;
    //关联的权重
    private int weight;

    public ClassLink(String uri1, String uri2, int weight) {
        this.uri1 = uri1;
        this.uri2 = uri2;
        this.weight = weight;
    }

    public String getUri1() {
        return uri1;
    }

    public void setUri1(String uri1) {
        this.uri1 = uri1;
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

    @Override
    public String toString() {
        return "ClassLink{" +
                "uri1='" + uri1 + '\'' +
                ", uri2='" + uri2 + '\'' +
                ", weight=" + weight +
                '}';
    }
}
