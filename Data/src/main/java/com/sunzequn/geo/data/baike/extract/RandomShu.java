package com.sunzequn.geo.data.baike.extract;

import java.util.Random;

/**
 * Created by sunzequn on 2016/4/20.
 */
public class RandomShu {

    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            System.out.println(random.nextInt(1000));
        }
    }
}
