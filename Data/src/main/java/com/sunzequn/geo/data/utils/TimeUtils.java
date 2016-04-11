package com.sunzequn.geo.data.utils;

import java.util.Date;

/**
 * Created by Sloriac on 15/12/20.
 */
public class TimeUtils {

    private long startTime;
    private long endTime;

    public void start() {
        startTime = new Date().getTime();
    }

    public void end() {
        endTime = new Date().getTime();
    }

    public long duration() {
        end();
        return endTime - startTime;
    }

    public void print() {
        System.out.println("cost: " + duration() + " milliseconds");
    }
}
