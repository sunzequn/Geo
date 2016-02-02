package com.sunzequn.geo.data.geonames.bean;

/**
 * Created by sloriac on 16-2-2.
 */
public class Id {

    private int id;
    private int ifvisited;

    public Id(int id, int ifvisited) {
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
}
