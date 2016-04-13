package com.sunzequn.geo.data.baike.bdmap;

/**
 * Created by sunzequn on 2016/4/12.
 */
public class Result {

    private Location location;
    private int precise;
    private int confidence;
    private String level;

    public Result() {
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getPrecise() {
        return precise;
    }

    public void setPrecise(int precise) {
        this.precise = precise;
    }

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Result{" +
                "location=" + location +
                ", precise=" + precise +
                ", confidence=" + confidence +
                ", level='" + level + '\'' +
                '}';
    }
}
