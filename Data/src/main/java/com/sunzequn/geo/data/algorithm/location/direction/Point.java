package com.sunzequn.geo.data.algorithm.location.direction;

import com.sunzequn.geo.data.algorithm.Earth;
import com.sunzequn.geo.data.algorithm.location.LatLngUtils;

/**
 * Created by Sloriac on 16/2/15.
 * 地球上一个具体的点(经纬度)的封装.
 */
public class Point {

    //地点的纬度(角度)
    private double lat;
    //地点的经度(角度)
    private double lng;
    //地点的纬度(弧度)
    private double radLat;
    //地点的经度(弧度)
    private double radLng;
    //当前点到地球球心的距离(地球在这个点的半径)
    private double actualRadius;
    //纬线圈的半径
    private double latRadius;

    /**
     * 不允许使用无参的构造方法
     */
    private Point() {
    }

    public Point(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
        radLat = LatLngUtils.deg2rad(lat);
        radLng = LatLngUtils.deg2rad(lng);
        actualRadius = Earth.RADIUS_POLAR + (Earth.RADIUS_EQUATOR - Earth.RADIUS_POLAR) * (90.0 - lat) / 90.0;
        latRadius = actualRadius * Math.cos(radLat);
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getRadLat() {
        return radLat;
    }

    public void setRadLat(double radLat) {
        this.radLat = radLat;
    }

    public double getRadLng() {
        return radLng;
    }

    public void setRadLng(double radLng) {
        this.radLng = radLng;
    }

    public double getActualRadius() {
        return actualRadius;
    }

    public void setActualRadius(double actualRadius) {
        this.actualRadius = actualRadius;
    }

    public double getLatRadius() {
        return latRadius;
    }

    public void setLatRadius(double latRadius) {
        this.latRadius = latRadius;
    }
}
