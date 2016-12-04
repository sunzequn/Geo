package com.sunzequn.geo.data.geonamesplus;

import java.util.List;

/**
 * 
 * @author sunzequn
 *
 */
public class PrecipitationDao extends BaseDao{
	
    private static final String TABLE = "ext_precipitation_monthly_recent10";
    
    /**
     * 根据主键（经度，纬度，年，月）查询
     *
     * @param lng 经度
     * @param lat 纬度
     * @param year 年份，限制取值[2006,2015]
     * @param month 月份，限制取值[1,12]
     * @return
     */
    public Precipitation getByKey(double lng, double lat, int year, int month) {
        String sql = "select * from " + TABLE + " where longitude = ? and latitude = ? and year = ? and month = ?";
        Object[] params = {lng, lat, year, month};
        List<Precipitation> precipitations = query(connection, sql, params, Precipitation.class);
        return precipitations == null ? null : precipitations.get(0); 
    }

    /**
     * 查询某地（经纬度）某年的全部月份的日均降水
     * @param lng 经度
     * @param lat 纬度
     * @param year 年份，限制取值[2006,2015]
     * @return
     */
    public List<Precipitation> getByYear(double lng, double lat, int year){
        String sql = "select * from " + TABLE + " where longitude = ? and latitude = ? and year = ?";
        Object[] params = {lng, lat, year};
        return query(sql, params);
    }
    
    /**
     * 查询某地（经纬度）全部的日均降水
     * @param lng 经度
     * @param lat 纬度
     * @return
     */
    public List<Precipitation> getAll(double lng, double lat){
        String sql = "select * from " + TABLE + " where longitude = ? and latitude = ?";
        Object[] params = {lng, lat};
        return query(sql, params);
    }

    private List<Precipitation> query(String sql, Object[] params){
        List<Precipitation> precipitations = query(connection, sql, params, Precipitation.class);
        return precipitations == null ? null : precipitations;
    }

}
