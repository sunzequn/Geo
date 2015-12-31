package com.sunzequn.geocities.data.china.geonames;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloriac on 15/12/31.
 */
public class Json {

    private String name;
    private List<Json> contains;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Json> getContains() {
        return contains;
    }

    public void setContains(List<Json> contains) {
        this.contains = contains;
    }
}
