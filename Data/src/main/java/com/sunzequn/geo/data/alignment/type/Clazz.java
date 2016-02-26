package com.sunzequn.geo.data.alignment.type;

import java.util.List;

/**
 * Created by Sloriac on 16/2/26.
 * <p>
 * 本体,类
 */
public class Clazz {

    //简写的实体(instance或者class)的uri
    private String uri;
    private List<Clazz> superClasses = null;
    private List<Clazz> equivalentClasses = null;

    public Clazz(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<Clazz> getSuperClasses() {
        return superClasses;
    }

    public void setSuperClasses(List<Clazz> superClasses) {
        this.superClasses = superClasses;
    }

    public List<Clazz> getEquivalentClasses() {
        return equivalentClasses;
    }

    public void setEquivalentClasses(List<Clazz> equivalentClasses) {
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
