package com.sunzequn.geo.data.china.city;

import com.google.gson.Gson;
import com.sunzequn.geo.data.china.city.tojson.Province;
import com.sunzequn.geo.data.china.city.tojson.China;
import com.sunzequn.geo.data.china.city.tojson.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloriac on 15/12/30.
 */
public class ToJson {

    private static China china = new China();
    private static CityDao cityDao = new CityDao();

    public static void main(String[] args) {
        removeBadData();
        handleData();
        json();
    }

    public static List<ChinaCity> getCounties(int cityId) {
        return cityDao.getCityByParent(cityId);
    }

    public static List<ChinaCity> getCities(int proviceId) {
        return cityDao.getCityByParent(proviceId);
    }

    public static List<ChinaCity> getProvinces() {
        return cityDao.getCityByLevel(1);
    }

    public static void handleData() {
        List<ChinaCity> provinces = getProvinces();
        List<Province> ps = new ArrayList<>();
        for (ChinaCity province : provinces) {
            Province p = new Province(province.getName());
            List<ChinaCity> cities = getCities(province.getId());
            List<City> cis = new ArrayList<>();
            for (ChinaCity city : cities) {
                City c = new City(city.getName());
                List<ChinaCity> countries = getCounties(city.getId());
                if (countries != null) {
                    List<String> cos = new ArrayList<>();
                    for (ChinaCity country : countries) {
                        cos.add(country.getName());
                    }
                    c.setCountries(cos);
                } else {
                    c.setCountries(null);
                }
                cis.add(c);
            }
            p.setCities(cis);
            ps.add(p);
        }
        china.setProvinces(ps);
    }

    public static void json() {
        Gson gson = new Gson();
        System.out.println(gson.toJson(china));
    }

    /**
     * 去除 直辖县级 这个名称,
     */
    public static void removeBadData() {
        List<ChinaCity> zhixiaxianji = cityDao.getCityByName("直辖县级");
        if (zhixiaxianji == null) {
            return;
        }
        for (ChinaCity city : zhixiaxianji) {
            List<ChinaCity> counties = getCounties(city.getId());
            for (ChinaCity country : counties) {
                int id = country.getId();
                String mergerName = country.getMergerName().replace(",直辖县级", "");
                int parentId = city.getParentId();
                int levelType = city.getLevelType();
                cityDao.updateBadData(id, mergerName, parentId, levelType);
            }
            cityDao.deleteById(city.getId());
        }
    }
}
