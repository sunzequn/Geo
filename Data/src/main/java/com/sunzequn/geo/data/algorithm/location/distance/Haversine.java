package com.sunzequn.geo.data.algorithm.location.distance;

import com.sunzequn.geo.data.algorithm.location.DegreeUtils;

/**
 * Created by Sloriac on 16/1/5.
 * <p>
 * 球面半正矢公式(haversine formula)计算地球两点之间距离等.
 * 开源库spatial4j有相应实现,具体应用的时候也可以直接使用spatial4j.
 */
public class Haversine {

    //地球平均半径,以米为单位,来源百度百科
    private static final int EARTH_RADIUS = 6371393;

    /**
     * 根据经纬度计算两点在水平面的距离(参考开源库spatial4j源码)
     *
     * @param lat1 第一个点的纬度
     * @param lon1 第一个点的经度
     * @param lat2 第二个点的纬度
     * @param lon2 第二个点的经度
     * @return 两点之间的距离, 以米为单位
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        //检查相同地点
        if (lat1 == lat2 && lon1 == lon2) {
            return 0.0;
        }

        /*
        角度转弧度
         */
        lat1 = DegreeUtils.deg2rad(lat1);
        lat2 = DegreeUtils.deg2rad(lat2);
        lon1 = DegreeUtils.deg2rad(lon1);
        lon2 = DegreeUtils.deg2rad(lon2);

        double havLat = haversine(lat2 - lat1);
        double havLon = haversine(lon2 - lon1);
        double h = havLat + Math.cos(lat1) * Math.cos(lat2) * havLon;
        if (h > 1) {
            h = 1;
        }
        return 2 * EARTH_RADIUS * Math.atan2(Math.sqrt(h), Math.sqrt(1 - h));
    }

    /**
     * haversine的计算函数
     *
     * @param radians 弧度
     * @return 角度的haversine值
     */
    public static double haversine(double radians) {
        return Math.pow(Math.sin(radians * 0.5), 2);
    }
}
