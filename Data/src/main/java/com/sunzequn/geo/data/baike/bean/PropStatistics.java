package com.sunzequn.geo.data.baike.bean;

/**
 * Created by sunzequn on 2016/4/5.
 */
public class PropStatistics {

    private InfoBoxTemplateProp prop;
    public long num = 0;
    public long objectNum = 0;
    public long datatypeNum = 0;
    public long stringNum = 0;
    public long intNum = 0;
    public long doubleNum = 0;

    public PropStatistics(InfoBoxTemplateProp prop) {
        this.prop = prop;
    }

    public InfoBoxTemplateProp getProp() {
        return prop;
    }

    public void setProp(InfoBoxTemplateProp prop) {
        this.prop = prop;
    }

    @Override
    public String toString() {
        return "PropStatistics{" +
                "datatypeNum=" + datatypeNum +
                ", prop=" + prop +
                ", num=" + num +
                ", objectNum=" + objectNum +
                ", StringNum=" + stringNum +
                ", intNum=" + intNum +
                ", doubleNum=" + doubleNum +
                '}';
    }
}
