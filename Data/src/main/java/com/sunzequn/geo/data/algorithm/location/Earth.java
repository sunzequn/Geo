package com.sunzequn.geo.data.algorithm.location;

/**
 * Created by Sloriac on 16/2/13.
 * 地球相关常量
 */
public final class Earth {

    /**
     * 不允许在其他地方实例化这个类
     */
    private Earth() {
    }

    /**
     * 地球平均半径,单位是米,数据来源维基百科:https://en.wikipedia.org/wiki/Earth_radius.
     */
    public static final double RADIUS = 6371000.0;

    /**
     * 地球赤道半径,单位是米,数据来源维基百科:https://en.wikipedia.org/wiki/Earth_radius#Equatorial_radius
     */
    public static final double RADIUS_EQUATOR = 6378137.0;

    /**
     * 地球极地半径,单位是米,数据来源维基百科:https://en.wikipedia.org/wiki/Earth_radius#Polar_radius
     */
    public static final double RADIUS_POLAR = 6356752.3;

}
