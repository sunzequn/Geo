package com.sunzequn.geo.data.geonames.filebean;

/**
 * Created by sloriac on 16-2-2.
 *
 *
 */
public class NoId {

    private int id;

    public NoId() {
    }

    public NoId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "NoId{" +
                "id=" + id +
                '}';
    }
}
