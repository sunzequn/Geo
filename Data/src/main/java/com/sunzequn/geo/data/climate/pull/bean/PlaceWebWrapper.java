package com.sunzequn.geo.data.climate.pull.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloriac on 16/2/10.
 */
public class PlaceWebWrapper {

    List<Place> places = new ArrayList<>();
    List<String> nexts = new ArrayList<>();

    public PlaceWebWrapper() {
    }

    public PlaceWebWrapper(List<Place> places, List<String> nexts) {
        this.places = places;
        this.nexts = nexts;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public List<String> getNexts() {
        return nexts;
    }

    public void setNexts(List<String> nexts) {
        this.nexts = nexts;
    }
}