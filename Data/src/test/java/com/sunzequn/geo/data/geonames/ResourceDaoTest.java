package com.sunzequn.geo.data.geonames;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sloriac on 16/1/21.
 */
public class ResourceDaoTest {

    @Test
    public void saveTest() {
        ResourceDao resourceDao = new ResourceDao("contains_url");
        Resource resource = new Resource(1, 0);
        resourceDao.save(resource);
    }

    @Test
    public void getUnvisitedTest() {
        ResourceDao resourceDao = new ResourceDao("contains_url");
        System.out.println(resourceDao.getUnvisited(10));
    }

    @Test
    public void updateTest() {
        ResourceDao resourceDao = new ResourceDao("contains_url");
        resourceDao.update(1, 1);
    }

}
