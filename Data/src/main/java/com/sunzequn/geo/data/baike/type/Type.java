package com.sunzequn.geo.data.baike.type;


import com.sunzequn.geo.data.baike.bean.Prop;

import java.util.List;

/**
 * Created by Sloriac on 16/3/22.
 */
public class Type {

    private int id;
    private String title;
    private List<Prop> props;

    public Type(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Type(int id, String title, List<Prop> props) {
        this.id = id;
        this.title = title;
        this.props = props;
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

    public List<Prop> getProps() {
        return props;
    }

    public void setProps(List<Prop> props) {
        this.props = props;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", props=" + props +
                '}';
    }
}
