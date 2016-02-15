package com.sunzequn.geo.data.algorithm.location.direction;

import com.sunzequn.geo.data.algorithm.location.LatLngUtils;

/**
 * Created by Sloriac on 16/2/15.
 */
public class DirAngle {

    /**
     * 计算方位角, 范围是 [0~360).
     *
     * @param lat1 第一个点的纬度(角度)
     * @param lng1 第一个点的经度(角度)
     * @param lat2 第二个点的纬度(角度)
     * @param lng2 第二个点的经度(角度)
     * @return 两点之间的方位角
     */
    public static double calculateAngle(double lat1, double lng1, double lat2, double lng2) {

        if (lat1 == lat2 && lng1 == lng2) {
            return 0;
        }

        Point point1 = new Point(lat1, lng1);
        Point point2 = new Point(lat2, lng2);
        //就算方向角
        double dx = (point2.getRadLng() - point1.getRadLng()) * point1.getLatRadius();
        double dy = (point2.getRadLat() - point1.getRadLat()) * point1.getActualRadius();
        double angle = LatLngUtils.rad2deg(Math.atan(Math.abs(dx / dy)));

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

    public static void main(String[] args) {
        System.out.println(calculateAngle(32, 120, 30, -60));
    }

}
