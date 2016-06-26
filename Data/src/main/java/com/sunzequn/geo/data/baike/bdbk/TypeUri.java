package com.sunzequn.geo.data.baike.bdbk;

/**
 * Created by sunzequn on 2016/4/28.
 */
public class TypeUri {

    private String uri;
    private String type;

    public TypeUri() {
    }

    public TypeUri(String uri, String type) {
        this.uri = uri;
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TypeUri{" +
                "uri='" + uri + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
