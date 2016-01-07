package com.sunzequn.geo.data.crawler.application.climate.pull.dao;

import com.sunzequn.geo.data.crawler.application.climate.pull.bean.Country;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Sloriac on 16/1/7.
 */
public class CountryDaoTest {
    private CountryDao countryDao = new CountryDao();

    @Test
    public void saveTest() {
        Country country = new Country(1111, "China", "url", 1, 0);
        countryDao.save(country);

    }

    @Test
    public void updateTest() {
        countryDao.update(1, 1);
    }

    @Test
    public void getAllTest() {
        System.out.println(countryDao.getAll().size());
    }

    @Test
    public void getUnvisitedTest() {
        System.out.println(countryDao.getUnvisited().size());
    }

}
