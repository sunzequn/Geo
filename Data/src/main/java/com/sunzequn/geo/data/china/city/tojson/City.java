package com.sunzequn.geo.data.china.city.tojson;

import java.util.List;

/**
 * Created by Sloriac on 15/12/30.
 */
public class City {

    private String name;
    private List<String> countries;

    public City(String name) {
        this.name = name;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }
}
