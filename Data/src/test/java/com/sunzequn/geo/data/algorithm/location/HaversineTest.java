package com.sunzequn.geo.data.algorithm.location;

import org.junit.Test;

/**
 * Created by Sloriac on 16/1/5.
 */
public class HaversineTest {

    @Test
    public void distanceTest() {
        System.out.println(Haversine.distance(39.941, 116.45, 39.94, 116.451));
    }
}
