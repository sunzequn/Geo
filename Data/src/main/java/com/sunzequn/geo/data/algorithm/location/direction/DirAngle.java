package com.sunzequn.geo.data.algorithm.location.direction;

import com.sunzequn.geo.data.algorithm.location.AngleUtils;
import com.sunzequn.geo.data.algorithm.location.DegreeUtils;
import com.sunzequn.geo.data.algorithm.location.LatLngUtils;

/**
 * Created by Sloriac on 16/2/15.
 *
 * 方向角计算类
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
        double angle = DegreeUtils.rad2deg(Math.atan(Math.abs(dx / dy)));
        //考虑象限问题
        return AngleUtils.newAngleAccordingToQuadrant(angle, lat1, lng1, lat2, lng2);
    }

    public static void main(String[] args) {
        System.out.println(calculateAngle(32, 120, 30, -60));
    }

}
