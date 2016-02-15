package com.sunzequn.geo.data.algorithm.location;

/**
 * Created by Sloriac on 16/2/15.
 * <p>
 * 角度弧度转化的工具类
 */
public class DegreeUtils {

    /**
     * 将角度转换为弧度
     *
     * @param degree 角度值
     * @return 对应的弧度值
     */
    public static double deg2rad(double degree) {
        return degree * Math.PI / 180;
    }

    /**
     * 将弧度转换为角度
     *
     * @param radians 弧度值
     * @return 对应的角度值
     */
    public static double rad2deg(double radians) {
        return radians * 180 / Math.PI;
    }

}
