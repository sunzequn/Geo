package com.sunzequn.geo.data.baike.bean;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloriac on 16/3/17.
 */
public class InfoBoxTemplate {

    private int id;
    private String title;
    private String entitle;
    private int parentid;
    private int level;
    private String prop;
    private String comment;
    private String encomment;
    private List<Prop> props;

    public InfoBoxTemplate() {
    }


    public void initProps() {
        props = new ArrayList<>();
        if (prop == null || prop.equals("")) {
            return;
        }
        String[] ps = StringUtils.split(prop, "/");
        for (String p : ps) {
            p = p.trim();
            String name = p, comment = null;
            if (p.contains("(")) {
                name = StringUtils.split(p, "(")[0];
                comment = StringUtils.removeEnd(StringUtils.split(p, "(")[1], ")");
            }
            if (name.contains(",")) {
                name = name.replace(",", "/");
            }
            props.add(new Prop(name, comment));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public List<Prop> getProps() {
        return props;
    }

    public void setProps(List<Prop> props) {
        this.props = props;
    }

    public String getEntitle() {
        return entitle;
    }

    public void setEntitle(String entitle) {
        this.entitle = entitle;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEncomment() {
        return encomment;
    }

    public void setEncomment(String encomment) {
        this.encomment = encomment;
    }

    @Override
    public String toString() {
        return "InfoBoxTemplate{" +
                "comment='" + comment + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", entitle='" + entitle + '\'' +
                ", parentid=" + parentid +
                ", level=" + level +
                ", prop='" + prop + '\'' +
                ", props=" + props +
                '}';
    }
}
