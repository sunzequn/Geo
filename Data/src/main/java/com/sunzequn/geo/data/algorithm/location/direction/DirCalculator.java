package com.sunzequn.geo.data.algorithm.location.direction;

import com.sunzequn.geo.data.algorithm.location.AngleUtils;
import com.sunzequn.geo.data.algorithm.location.Direction;

/**
 * Created by Sloriac on 16/2/16.
 */
public class DirCalculator {

    private static final double HALF_NORTH_ANGLE = 22.5;

    /**
     * 根据经纬度计算第二个点相对于第一个点的方向
     *
     * @param lat1 第一个点的纬度(角度)
     * @param lng1 第一个点的经度(角度)
     * @param lat2 第二个点的纬度(角度)
     * @param lng2 第二个点的经度(角度)
     * @return 第二个点相对于第一个点的方向
     */
    public Direction direction(double lat1, double lng1, double lat2, double lng2) {
        double angle = DirAngle.calculateAngle(lat1, lng1, lat2, lng2);
        if (angle != 0) {
            return AngleUtils.judgeDirectionByAngle(angle, HALF_NORTH_ANGLE);
        }
        return null;
    }

}
