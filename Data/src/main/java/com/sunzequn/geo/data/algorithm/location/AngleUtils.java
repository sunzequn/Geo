package com.sunzequn.geo.data.algorithm.location;

/**
 * Created by Sloriac on 16/2/15.
 * <p>
 * 关于角计算的工具类
 */
public class AngleUtils {

    /**
     * 对于两条直线的夹角,考虑象限问题之后,对角度进行修正
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

    /**
     * 根据角度(方向角或者方位角)判断属于哪个基本方向.
     * 基本方向有8个,参考枚举类型Direction.
     *
     * @param angle          角度,正常范围是[0,360)
     * @param halfNorthAngle 正北方向夹角范围的一半
     * @return 这个角度所属的基本方向
     */
    public static Direction judgeDirectionByAngle(double angle, double halfNorthAngle) {
        if (angle < 0 || angle >= 360 || halfNorthAngle < 0 || halfNorthAngle >= 45.0) {
            return null;
        }
        if (angle >= halfNorthAngle && angle <= (90 - halfNorthAngle)) {
            return Direction.NORTHEAST;
        } else if (angle > (90 - halfNorthAngle) && angle < (90 + halfNorthAngle)) {
            return Direction.EAST;
        } else if (angle >= (90 + halfNorthAngle) && angle <= (180 - halfNorthAngle)) {
            return Direction.SOUTHEAST;
        } else if (angle > (180 - halfNorthAngle) && angle < (180 + halfNorthAngle)) {
            return Direction.SOUTH;
        } else if (angle >= (180 + halfNorthAngle) && angle <= (270 - halfNorthAngle)) {
            return Direction.SOUTHWEST;
        } else if (angle > (270 - halfNorthAngle) && angle < (270 + halfNorthAngle)) {
            return Direction.WEST;
        } else if (angle >= (270 + halfNorthAngle) && angle <= (360 - halfNorthAngle)) {
            return Direction.NORTHWEST;
        } else {
            return Direction.NORTH;
        }
    }

}
