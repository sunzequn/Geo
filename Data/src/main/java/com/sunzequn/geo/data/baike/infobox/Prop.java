package com.sunzequn.geo.data.baike.infobox;

/**
 * Created by Sloriac on 16/3/17.
 */
public class Prop {

    private String name;
    private String comment;

    public Prop(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Prop{" +
                "name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
