package com.sunzequn.geo.data.algorithm.location;

/**
 * Created by Sloriac on 16/2/15.
 * <p>
 * 关于角计算的工具类
 */
public class AngleUtils {

    /**
     * 考虑象限问题之后,对角度进行修正
     *
     * @param angle 两条线的夹角,取值范围一般是0-90
     * @param lat1  第一个点的纬度(角度)
     * @param lng1  第一个点的经度(角度)
     * @param lat2  第二个点的纬度(角度)
     * @param lng2  第二个点的经度(角度)
     * @return 结合象限进行修正之后的角度
     */
    public static double newAngleAccordingToQuadrant(double angle, double lat1, double lng1, double lat2, double lng2) {

        //判断象限
        int quadrant = LatLngUtils.calculateQuadrant(lat1, lng1, lat2, lng2);
        //第二象限或者x轴负半轴
        if (quadrant == 2 || quadrant == 6) {
            angle = 360.0 - angle;
        }
        //第三象限或者y轴负半轴
        else if (quadrant == 3 || quadrant == 8) {
            angle = 180.0 + angle;
        }
        //第四象限或者x轴正半轴或者对称经线南部
        else if (quadrant == 4 || quadrant == 5 || quadrant == 10) {
            angle = 180.0 - angle;
        }

        //其他情况不需要处理

        return angle;
    }
}
