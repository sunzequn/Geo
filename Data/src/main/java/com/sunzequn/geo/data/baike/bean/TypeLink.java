package com.sunzequn.geo.data.baike.bean;

/**
 * Created by sunzequn on 2016/4/5.
 */
public class TypeLink {

    private String type;
    private String relation;
    private String entity;
    private String comment;

    public TypeLink() {
    }

    public TypeLink(String comment, String entity, String relation, String type) {
        this.comment = comment;
        this.entity = entity;
        this.relation = relation;
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TypeLink{" +
                "comment='" + comment + '\'' +
                ", type='" + type + '\'' +
                ", relation='" + relation + '\'' +
                ", entity='" + entity + '\'' +
                '}';
    }
}
