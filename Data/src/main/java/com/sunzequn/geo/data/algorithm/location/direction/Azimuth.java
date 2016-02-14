package com.sunzequn.geo.data.algorithm.location.direction;

import com.sunzequn.geo.data.algorithm.location.LatLngUtils;

/**
 * Created by Sloriac on 16/2/13.
 * 方位角计算类
 */
public class Azimuth {


    /**
     * 根据两个点的经纬度,计算第二个点相对于第一个点的方位角
     *
     * @param lat1 第一个点的纬度(角度)
     * @param lng1 第一个点的经度(角度)
     * @param lat2 第二个点的纬度(角度)
     * @param lng2 第二个点的经度(角度)
     * @return 第二个点相对于第一个点的方位角(未经象限判断)
     */
    public static double calculate(double lat1, double lng1, double lat2, double lng2) {

        lat1 = LatLngUtils.deg2rad(lat1);
        lat2 = LatLngUtils.deg2rad(lat2);
        lng1 = LatLngUtils.deg2rad(lng1);
        lng2 = LatLngUtils.deg2rad(lng2);

        double cos = Math.sin(lat2) * Math.sin(lat1) + Math.cos(lat2) * Math.cos(lat1) * Math.cos(lng2 - lng1);
        double sin = Math.sqrt(1 - cos * cos);
        double radians = Math.asin(Math.cos(lat2) * Math.sin(lng2 - lng1) / sin);
        return LatLngUtils.rad2deg(radians);
    }

}
