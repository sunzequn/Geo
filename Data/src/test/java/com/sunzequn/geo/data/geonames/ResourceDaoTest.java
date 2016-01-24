package com.sunzequn.geo.data.geonames;

import com.sunzequn.geo.data.geonames.bean.Resource;
import com.sunzequn.geo.data.geonames.bean.ResourceDao;
import org.junit.Test;

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
