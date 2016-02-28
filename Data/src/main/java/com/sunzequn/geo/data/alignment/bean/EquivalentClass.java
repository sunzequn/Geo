package com.sunzequn.geo.data.alignment.bean;

/**
 * Created by Sloriac on 16/2/27.
 * 等价类,把参与计算的类存放在uri1
 */
public class EquivalentClass {
    private String uri1;
    private String uri2;

    public EquivalentClass() {
    }

    public EquivalentClass(String uri1, String uri2) {
        this.uri1 = uri1;
        this.uri2 = uri2;
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

    @Override
    public String toString() {
        return "EquivalentClass{" +
                "uri1='" + uri1 + '\'' +
                ", uri2='" + uri2 + '\'' +
                '}';
    }
}
