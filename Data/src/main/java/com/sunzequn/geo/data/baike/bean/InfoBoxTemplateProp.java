package com.sunzequn.geo.data.baike.bean;

/**
 * Created by sloriac on 16-3-29.
 */
public class InfoBoxTemplateProp {

    private int id;
    private String name;
    private int templateid;
    //属性类型，0代表datatype, 1代表object
    private int type;
    private String comment;

    public InfoBoxTemplateProp() {
    }

    public InfoBoxTemplateProp(String comment, String name, int templateid, int type) {
        this.comment = comment;
        this.name = name;
        this.templateid = templateid;
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTemplateid() {
        return templateid;
    }

    public void setTemplateid(int templateid) {
        this.templateid = templateid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "InfoBoxTemplateProp{" +
                "comment='" + comment + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", templateid=" + templateid +
                ", type=" + type +
                '}';
    }
}
