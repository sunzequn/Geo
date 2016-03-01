package com.sunzequn.geo.data.alignment.bean;

/**
 * Created by Sloriac on 16/2/29.
 */
public class UpperClass {

    private String superuri;

    public UpperClass() {
    }

    public UpperClass(String superuri) {
        this.superuri = superuri;
    }

    public String getSuperuri() {
        return superuri;
    }

    public void setSuperuri(String superuri) {
        this.superuri = superuri;
    }


    @Override
    public String toString() {
        return "DbpediaUpperClass{" +
                "superuri='" + superuri + '\'' +
                '}';
    }
}
