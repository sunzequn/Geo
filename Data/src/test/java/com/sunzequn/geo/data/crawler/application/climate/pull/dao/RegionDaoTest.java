package com.sunzequn.geo.data.crawler.application.climate.pull.dao;

import com.sunzequn.geo.data.crawler.application.climate.pull.bean.Region;
import org.junit.Test;

/**
 * Created by Sloriac on 16/1/7.
 */
public class RegionDaoTest {

    private RegionDao regionDao = new RegionDao();

    @Test
    public void saveTest() {
        Region region = new Region(1, "nanjing", "url", 1, 0);
        regionDao.save(region);
    }

    @Test
    public void getAllTest() {
        System.out.println(regionDao.getAll().size());
    }

    @Test
    public void getUnvisitedTest() {
        System.out.println(regionDao.getUnvisited().size());
    }

    @Test
    public void updateTest() {
        regionDao.update(1, 1);
    }
}
