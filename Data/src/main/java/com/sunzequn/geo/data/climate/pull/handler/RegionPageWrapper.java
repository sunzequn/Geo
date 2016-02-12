package com.sunzequn.geo.data.climate.pull.handler;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sloriac on 16-2-12.
 */
public class RegionPageWrapper {

    private int id;
    private Set<Integer> pages = new HashSet<>();

    public RegionPageWrapper() {
    }

    public RegionPageWrapper(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Integer> getPages() {
        return pages;
    }

    public void setPages(Set<Integer> pages) {
        this.pages = pages;
    }
}
