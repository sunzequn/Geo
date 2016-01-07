package com.sunzequn.geo.data.china.division.tojson;

import java.util.List;

/**
 * Created by Sloriac on 15/12/30.
 */
public class Province {

    private String name;
    private List<City> cities;

    public Province(String name) {
        this.name = name;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
