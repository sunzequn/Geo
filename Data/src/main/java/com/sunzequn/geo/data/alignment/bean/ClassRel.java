package com.sunzequn.geo.data.alignment.bean;

/**
 * Created by Sloriac on 16/2/27.
 *
 * class和其父类的关系
 */
public class ClassRel {

    private String uri;
    private String superuri;

    public ClassRel() {
    }

    public ClassRel(String uri, String superuri) {
        this.uri = uri;
        this.superuri = superuri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getSuperuri() {
        return superuri;
    }

    public void setSuperuri(String superuri) {
        this.superuri = superuri;
    }

    @Override
    public String toString() {
        return "TypeRel{" +
                "uri='" + uri + '\'' +
                ", superuri='" + superuri + '\'' +
                '}';
    }
}
