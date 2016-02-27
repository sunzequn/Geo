package com.sunzequn.geo.data.alignment.bean;

/**
 * Created by Sloriac on 16/2/27.
 */
public class EntityType {

    private String uri;
    private String typeclass;

    public EntityType(String uri, String typeclass) {
        this.uri = uri;
        this.typeclass = typeclass;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTypeclass() {
        return typeclass;
    }

    public void setTypeclass(String typeclass) {
        this.typeclass = typeclass;
    }

    @Override
    public String toString() {
        return "EntityType{" +
                "uri='" + uri + '\'' +
                ", typeclass='" + typeclass + '\'' +
                '}';
    }
}
