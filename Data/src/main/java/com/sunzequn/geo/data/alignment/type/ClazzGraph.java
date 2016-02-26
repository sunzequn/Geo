package com.sunzequn.geo.data.alignment.type;

import java.util.List;

/**
 * Created by Sloriac on 16/2/26.
 */
public class ClazzGraph {

    //简写的类(class)的uri
    private String uri;
    private Clazz directSuperClass = null;
    private List<Clazz> equivalentClasses = null;

    public ClazzGraph(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Clazz getDirectSuperClass() {
        return directSuperClass;
    }

    public void setDirectSuperClass(Clazz directSuperClass) {
        this.directSuperClass = directSuperClass;
    }

    public List<Clazz> getEquivalentClasses() {
        return equivalentClasses;
    }

    public void setEquivalentClasses(List<Clazz> equivalentClasses) {
        this.equivalentClasses = equivalentClasses;
    }

    @Override
    public String toString() {
        return "ClazzGraph{" +
                "uri='" + uri + '\'' +
                ", directSuperClass=" + directSuperClass +
                ", equivalentClasses=" + equivalentClasses +
                '}';
    }
}
