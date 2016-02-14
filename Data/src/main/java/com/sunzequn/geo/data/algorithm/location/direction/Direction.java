package com.sunzequn.geo.data.algorithm.location.direction;

import com.sunzequn.geo.data.algorithm.location.DegreeUtils;

/**
 * Created by Sloriac on 16/2/13.
 * 方向计算类
 */
public class Direction {

    /**
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double calculateAzimuth(double lat1, double lng1, double lat2, double lng2) {

        lat1 = DegreeUtils.deg2rad(lat1);
        lat2 = DegreeUtils.deg2rad(lat2);
        lng1 = DegreeUtils.deg2rad(lng1);
        lng2 = DegreeUtils.deg2rad(lng2);

        double cos = Math.sin(lat2) * Math.sin(lat1) + Math.cos(lat2) * Math.cos(lat1) * Math.cos(lng2 - lng1);
        double sin = Math.sqrt(1 - cos * cos);
        double radians = Math.asin(Math.cos(lat2) * Math.sin(lng2 - lng1) / sin);
        double degree = DegreeUtils.rad2deg(radians);
        int quadrant = calculateQuadrant(lat1, lng1, lat2, lng2);
        if (quadrant == 2) {
            degree += 360;
        } else if (quadrant == 3 || quadrant == 4) {
            degree = 180.0 - degree;
        }
        return degree;
    }

    /**
     * 以第一个点为原点,以正南北(经度线)为y轴,以正东西(纬度线)为x轴,判断第二个点在哪个象限或者坐标轴.
     *
     * @param lat1 第一个点的纬度(角度)
     * @param lng1 第一个点的经度(角度)
     * @param lat2 第二个点的纬度(角度)
     * @param lng2 第二个点的经度(角度)
     * @return 第二个点所在的象限. 其中, 1, 2, 3, 4分别代表四个象限, 5代表在x轴(同一个纬线), 6代表在y轴(同一个经线), 7代表在同一个经线圈.
     */
    public static int calculateQuadrant(double lat1, double lng1, double lat2, double lng2) {
        if (lat1 == lat2) {
            return 5;
        }
        if (lng1 == lng2) {
            return 6;
        }

        int isEastOrWest = calculateEastOrWest(lng1, lng2);
        //东边,一,四象限
        if (isEastOrWest == 1) {
            //北边,第一象限
            if (lat2 > lat1) {
                return 1;
            }
            //南边,第四象限
            else {
                return 4;
            }
        }
        //西边,二,三象限
        else if (isEastOrWest == 2) {
            //北边,第二象限
            if (lat2 > lat1) {
                return 2;
            }
            //南边,第三象限
            else {
                return 3;
            }
        } else {
            return 7;
        }
    }

    /**
     * 根据经度判断第二个点在第一个点的东边还是西边.
     *
     * @param lng1 第一个点的经度(角度)
     * @param lng2 第二个点的经度(角度)
     * @return 如果第二个点在第一个点的东边, 返回1;在西边,返回2;如果两个点在经线圈上,返回0;如果两个点在同一个经线上,返回-1.
     */
    public static int calculateEastOrWest(double lng1, double lng2) {

        //两个点在一条经线上
        if (lng1 == lng2) {
            return -1;
        }

        //两个点在一个经线圈上
        if (Math.abs(lng1) + Math.abs(lng2) == 180.0) {
            return 0;
        }

        //lng1为0时特判,此时,lng2不可能为-180或者180
        if (lng1 == 0) {
            if (lng2 > lng1) {
                return 1;
            } else {
                return 2;
            }
        }
        // 当lng1不为0时,计算对称的经线,分类讨论
        else {
            double symLng1 = lng1 > 0 ? lng1 - 180.0 : lng1 + 180.0;
            if (lng1 * lng2 > 0.0) {
                if (lng2 > lng1) {
                    return 1;
                } else {
                    return 2;
                }
            } else {
                if (lng2 > symLng1) {
                    return 1;
                } else {
                    return 2;
                }
            }
        }
    }


    public static void main(String[] args) {

        System.out.println(calculateAzimuth(32, 120, 31, 150));

    }

}
