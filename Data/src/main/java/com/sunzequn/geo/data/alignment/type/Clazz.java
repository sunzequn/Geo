package com.sunzequn.geo.data.alignment.type;

import java.util.List;

/**
 * Created by Sloriac on 16/2/26.
 * <p>
 * 本体,类
 */
public class Clazz {

    private String uri;
    private List<String> superClasses = null;

    public Clazz(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<String> getSuperClasses() {
        return superClasses;
    }

    public void setSuperClasses(List<String> superClasses) {
        this.superClasses = superClasses;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "uri='" + uri + '\'' +
                ", superClasses=" + superClasses +
                '}';
    }
}
