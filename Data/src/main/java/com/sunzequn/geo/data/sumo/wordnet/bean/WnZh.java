package com.sunzequn.geo.data.sumo.wordnet.bean;

import java.math.BigDecimal;

/**
 * Created by Sloriac on 16/3/9.
 */
public class WnZh implements Comparable<WnZh> {

    private BigDecimal synset_id;
    private BigDecimal w_num;
    private String chinese;
    private String zhKey;
//    private double

    public WnZh() {
    }

    public WnZh(BigDecimal synset_id, BigDecimal w_num, String chinese) {
        this.synset_id = synset_id;
        this.w_num = w_num;
        this.chinese = chinese;
    }

    public BigDecimal getSynset_id() {
        return synset_id;
    }

    public void setSynset_id(BigDecimal synset_id) {
        this.synset_id = synset_id;
    }

    public BigDecimal getW_num() {
        return w_num;
    }

    public void setW_num(BigDecimal w_num) {
        this.w_num = w_num;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getZhKey() {
        return zhKey;
    }

    public void setZhKey(String zhKey) {
        this.zhKey = zhKey;
    }

    @Override
    public String toString() {
        return "WnZh{" +
                "synset_id=" + synset_id +
                ", w_num=" + w_num +
                ", chinese='" + chinese + '\'' +
                ", zhKey='" + zhKey + '\'' +
                '}';
    }

    @Override
    public int compareTo(WnZh o) {
        double p1 = (double) zhKey.length() / (double) chinese.length();
        double p2 = (double) o.zhKey.length() / (double) o.chinese.length();
        if (p1 > p2) {
            return -1;
        } else if (p1 < p2) {
            return 1;
        } else {
            return 0;
        }
    }
}
