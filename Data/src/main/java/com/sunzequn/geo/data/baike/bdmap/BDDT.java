package com.sunzequn.geo.data.baike.bdmap;

/**
 * Created by sunzequn on 2016/4/12.
 */
public class BDDT {

    private int status;
    private Result result;

    public BDDT() {
    }

    public boolean isValid() {
        return status == 0;
    }

    public double getLng() {
        return result.getLocation().getLng();
    }

    public double getLat() {
        return result.getLocation().getLat();
    }

    public int getPrecise() {
        return result.getPrecise();
    }

    public int getConfidence() {
        return result.getConfidence();
    }

    public String getLevel() {
        return result.getLevel();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BDDT{" +
                "status=" + status +
                ", result=" + result +
                '}';
    }
}
