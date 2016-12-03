package com.sunzequn.geo.data.geonamesplus;

import java.util.Date;

/**
 * 
 * @author sunzequn
 *
 */
public class ClockUtils {

    private long startTime;
    private long endTime;
    
    public ClockUtils(){
    	start();
    }

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

    public void print(String content) {
        System.out.println(content + ", cost: " + duration() + " milliseconds");
    }
}
