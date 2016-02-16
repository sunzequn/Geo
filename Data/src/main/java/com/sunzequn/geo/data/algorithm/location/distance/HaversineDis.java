package com.sunzequn.geo.data.algorithm.location.distance;

import com.sunzequn.geo.data.algorithm.location.constant.Earth;
import com.sunzequn.geo.data.algorithm.location.utils.DegreeUtils;

/**
 * Created by Sloriac on 16/2/5.
 * <p>
 * 使用球面半正矢公式(haversine formula)计算地球两点之间距离等.
 */
public class HaversineDis {

    /**
     * 根据经纬度计算两点在水平面的距离(参考开源库spatial4j源码)
     *
     * @param lat1 第一个点的纬度(角度)
     * @param lon1 第一个点的经度(角度)
     * @param lat2 第二个点的纬度(角度)
     * @param lon2 第二个点的经度(角度)
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
        return 2 * Earth.RADIUS * Math.atan2(Math.sqrt(h), Math.sqrt(1 - h));
    }

    /**
     * 计算给定点和半径(距离)所作的圆在纬度方向上的半径(经度差).
     * 根据haversine公式计算.
     *
     * @param lat    给定点的纬度(角度)
     * @param radius 给定的半径,单位是米
     * @return 给定点和半径(距离)所作的圆在纬度方向上的经度差(角度)
     */
    public static double lonRadius(double lat, double radius) {
        if (radius < 0) {
            return 0;
        }
        lat = DegreeUtils.deg2rad(lat);
        double radians = 2 * Math.asin(Math.sin(radius / (2 * Earth.RADIUS)) / Math.cos(lat));
        return DegreeUtils.rad2deg(radians);
    }

    /**
     * 计算给定点和半径(距离)所作的圆在经度方向上的半径(纬度差).
     * 根据haversine公式计算.
     *
     * @param radius 给定的半径,单位是米
     * @return 给定点和半径(距离)所作的圆在经度方向上的纬度差(角度)
     */
    public static double latRadius(double radius) {
        if (radius < 0) {
            return 0;
        }
        double radians = radius / Earth.RADIUS;
        return DegreeUtils.rad2deg(radians);
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

    public static void main(String[] args) {
        System.out.println(distance(39.26, 115.25, 39.26, 117.30));//176382.73732179264
        System.out.println(lonRadius(39.26, 176382.73732179264));
        System.out.println(117.30 - 115.25);

        System.out.println(latRadius(distance(39.26, 115.25, -30.89, 115.25)));
    }
}
