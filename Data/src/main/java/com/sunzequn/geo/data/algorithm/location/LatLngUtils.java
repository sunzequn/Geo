package com.sunzequn.geo.data.algorithm.location;

/**
 * Created by Sloriac on 16/2/5.
 * <p>
 * 地点经纬度相关计算的工具类
 */
public class LatLngUtils {

    /**
     * 以第一个点为原点, 以正南北(经度线)为y轴, 以正东西(纬度线)为x轴, 判断第二个点在哪个象限或者坐标轴.
     * 返回值是数字代号, 其中, 1, 2, 3, 4分别代表四个象限;
     * 0代表两点重合;
     * 5代表在x轴正半轴, 6代表在x轴负半轴;
     * 7代表在y轴正半轴, 8代表在y轴负半轴;
     * 9代表在对称经线,且水平偏北, 10代表在对称经线,且水平偏南, 11代表在对称经线, 且同一个纬线圈.
     * 这里double的精度问题未做考虑,因为一般计算的时候,经纬度的值是直接给出的,应该不需要考虑精度问题.
     *
     * @param lat1 第一个点的纬度(角度)
     * @param lng1 第一个点的经度(角度)
     * @param lat2 第二个点的纬度(角度)
     * @param lng2 第二个点的经度(角度)
     * @return 第二个点所在的象限或者坐标轴的代号.
     */
    public static int calculateQuadrant(double lat1, double lng1, double lat2, double lng2) {

        int isEastOrWest = calculateEastOrWest(lng1, lng2);

        //东边,一,四象限和x轴正半轴
        if (isEastOrWest == 1) {
            //北边,第一象限
            if (lat2 > lat1) {
                return 1;
            }
            //南边,第四象限
            else if (lat2 < lat1) {
                return 4;
            }
            //x轴正半轴
            else {
                return 5;
            }
        }
        //西边,二,三象限
        else if (isEastOrWest == 2) {
            //北边,第二象限
            if (lat2 > lat1) {
                return 2;
            }
            //南边,第三象限
            else if (lat2 < lat1) {
                return 3;
            }
            //x轴负半轴
            else {
                return 6;
            }
        }
        //两点在同一条经线
        else if (isEastOrWest == -1) {
            //北边,y轴正半轴
            if (lat2 > lat1) {
                return 7;
            }
            //南边,y轴负半轴
            else if (lat2 < lat1) {
                return 8;
            }
            //原点
            else {
                return 0;
            }
        }
        //两点在同一个经线圈
        else {
            //北边
            if (lat2 > lat1) {
                return 9;
            }
            //南边
            else if (lat2 < lat1) {
                return 10;
            }
            //同一个纬线圈
            else {
                return 11;
            }
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
}
