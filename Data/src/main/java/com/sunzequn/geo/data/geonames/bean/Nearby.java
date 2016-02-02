package com.sunzequn.geo.data.geonames.bean;

/**
 * Created by sloriac on 16-2-2.
 */
public class Nearby {

    private int id;
    private String content;
    private int ifhandled;

    public Nearby() {
    }

    public Nearby(int id, String content, int ifhandled) {
        this.id = id;
        this.content = content;
        this.ifhandled = ifhandled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIfhandled() {
        return ifhandled;
    }

    public void setIfhandled(int ifhandled) {
        this.ifhandled = ifhandled;
    }
}
