package com.sunzequn.geo.data.geonames.statistic;

/**
 * Created by Sloriac on 16/3/23.
 */
public class StatisticInfo {

    private String fcode;
    private int num;
    private String name;
    private String description;

    public StatisticInfo() {
    }

    public StatisticInfo(String fcode, int num, String name, String description) {
        this.fcode = fcode;
        this.num = num;
        this.name = name;
        this.description = description;
    }

    public String getFcode() {
        return fcode;
    }

    public void setFcode(String fcode) {
        this.fcode = fcode;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "StatisticInfo{" +
                "fcode='" + fcode + '\'' +
                ", num=" + num +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
