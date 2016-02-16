package com.sunzequn.geo.data.geonames.filebean;

/**
 * Created by Sloriac on 16/1/21.
 */
public class Resource {

    private int id;
    private int ifvisited;

    public Resource() {
    }

    public Resource(int id, int ifvisited) {
        this.id = id;
        this.ifvisited = ifvisited;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIfvisited() {
        return ifvisited;
    }

    public void setIfvisited(int ifvisited) {
        this.ifvisited = ifvisited;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", ifvisited=" + ifvisited +
                '}';
    }
}
