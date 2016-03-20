package com.sunzequn.geo.data.china.geo;

import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.ReadUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by Sloriac on 16/3/19.
 */
public class CityHandler {

    private static final String CITY_FILE = "Data/src/main/resources/data/china_geo/city";
    private static ChinaCityDao chinaCityDao = new ChinaCityDao();

    public static void main(String[] args) {
        checkIfLngLat();
    }

    /**
     * 检查一下,已有的中国城市中包含地理基础信息城市多少
     */
    public static void checkIfLngLat() {
        ReadUtils readUtils = new ReadUtils(CITY_FILE);
        List<String> cities = readUtils.readByLine();
        if (ListUtils.isEmpty(cities)) {
            return;
        }
        System.out.println("一共有城市: " + cities.size());
        int matchedNum = 0;
        for (String city : cities) {
            List<ChinaCity> matchedCities = chinaCityDao.getByName(city);
            if (matchedCities != null) {
                matchedNum++;
            } else {
                if (city.endsWith("县")) {
                    city = StringUtils.removeEnd(city, "县");
                    List<ChinaCity> matchedCities1 = chinaCityDao.getByName(city);
                    if (matchedCities1 != null) {
                        matchedNum++;
                    } else {
                        System.out.println(city);
                    }
                }

            }
        }
        System.out.println("包含的数量: " + matchedNum);
    }

}
