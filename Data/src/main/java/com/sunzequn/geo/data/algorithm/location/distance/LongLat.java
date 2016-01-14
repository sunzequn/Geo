package com.sunzequn.geo.data.algorithm.location.distance;

/**
 * Created by Sloriac on 16/1/5.
 * <p>
 * 经纬度的模糊匹配
 * 可以使用开源库spatial4j中的距离判断公式
 * 其实根据Haversine也可以进行推导
 */
public class LongLat {

    /**
     * 判断两个点在设定的距离阈值下是否相近
     *
     * @param lat1     第一个点的纬度
     * @param lon1     第一个点的经度
     * @param lat2     第二个点的纬度
     * @param lon2     第二个点的经度
     * @param distance 设定的距离阈值
     * @return 如果两点距离小于或等于设定的阈值, 则返回真, 否则返回假
     */
    public static boolean isNear(double lat1, double lon1, double lat2, double lon2, double distance) {
        if (Haversine.distance(lat1, lon1, lat2, lon2) <= distance) {
            return true;
        }
        return false;
    }


}
