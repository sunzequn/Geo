package com.sunzequn.geo.data.climate.pull.handler;

import com.sunzequn.geo.data.climate.bean.Region;
import com.sunzequn.geo.data.climate.dao.RegionDao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sloriac on 16-2-14.
 */
public class RegionCheck {

    private static RegionDao regionDao = new RegionDao();

    public static void main(String[] args) {
        List<Region> regions = regionDao.getAll();
        Set<Integer> all = new HashSet<>();
        for (int i = 1; i <= 2653; i++) {
            all.add(i);
        }
        Set<Integer> visited = new HashSet<>();
        for (Region region : regions) {
            visited.add(region.getId());
        }
        all.removeAll(visited);
        System.out.println(all);


    }
}
