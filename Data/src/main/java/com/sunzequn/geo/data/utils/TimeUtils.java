package com.sunzequn.geo.data.utils;

import java.util.Date;

/**
 * Created by Sloriac on 15/12/20.
 */
public class TimeUtils {

    private static long startTime;
    private static long endTime;

    public static void start() {
        startTime = new Date().getTime();
    }

    public static void end() {
        endTime = new Date().getTime();
    }

    public static long duration() {
        return endTime - startTime;
    }

    public static void print() {
        System.out.println("cost: " + duration() + " milliseconds");
    }
}
