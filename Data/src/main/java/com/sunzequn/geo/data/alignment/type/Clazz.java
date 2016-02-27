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
    private List<String> equivalentClasses = null;

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

    public List<String> getEquivalentClasses() {
        return equivalentClasses;
    }

    public void setEquivalentClasses(List<String> equivalentClasses) {
        this.equivalentClasses = equivalentClasses;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "uri='" + uri + '\'' +
                ", superClasses=" + superClasses +
                ", equivalentClasses=" + equivalentClasses +
                '}';
    }
}
