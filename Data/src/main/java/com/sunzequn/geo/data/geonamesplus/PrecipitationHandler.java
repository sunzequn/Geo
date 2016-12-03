package com.sunzequn.geo.data.geonamesplus;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PrecipitationHandler {
	
	// 查询降水数据的年份，默认是近十年
	private static final int TOTAL_YEAR = 10;
	//年均降水量过少与适中的阈值,mm
	private static final int MIN_MID_THRESHOLD = 500;
	//年均降水量过少与适中的阈值,mm
	private static final int MID_MAX_THRESHOLD = 2000;
	//平均年际变化大小阈值,mm
	private static final int NIANJI_MIN_MAX_THRESHOLD = 300;
	//北半球夏季月份
	private static final int[] SUMMER_MONTH_NORTH = {5, 6, 7, 8};
	//北半球冬季月份
	private static final int[] WINTER_MONTH_NORTH = {1, 2, 11, 12};
	//降水集中阈值,mm
	private static final int JINGZHONG_THRESHOLD = 400;
    private PrecipitationDao precipitationDao = new PrecipitationDao();
    private FindGrid findGrid = new FindGrid();
    private List<List<Grid>> gpcpGrid = gpcpGrid();
	private static final Log log = LogFactory.getLog(PrecipitationHandler.class);
	
	public List<String> precipitationDescription(double lng, double lat){
		double p = avgPrecipitataionOfPerYear(lng, lat);
		List<String> des = new ArrayList<>();
		if (p == -1.0) {
			return null;
		}
		if (p < MIN_MID_THRESHOLD) {
			des.add("降水过少");
		} else if (p < MID_MAX_THRESHOLD) {
			des.add("降水适中");
		} else {
			des.add("降水过多");
		}
		List<Double> ps = precipitataionOfPerYear(lng, lat);
		double diff = 0;
		for(int i = 0; i < TOTAL_YEAR - 1; i++){
			diff += Math.abs(ps.get(i+1) - ps.get(i));
		}
		diff = diff / (TOTAL_YEAR - 1);
		if (diff < NIANJI_MIN_MAX_THRESHOLD) {
			des.add("年际变化小");
		} else {
			des.add("年际变化大");
		}
		return des;
	}
   
    /**
     * 获得某地（经纬度）的年均降水量（最近十年平均值）
     * @param lng
     * @param lat
     * @return 单位毫米
     */
    public double avgPrecipitataionOfPerYear(double lng, double lat){
    	List<Double> ps = precipitataionOfPerYear(lng, lat);
    	if (ps != null) {
			double res = 0;
			for(double p : ps){
				res += p;
			}
			return res / TOTAL_YEAR;
		}
    	return -1.0;
    }
    
    
    /**
     * 近十年的年降水总量
     * @param lng
     * @param lat
     * @return
     */
    public List<Double> precipitataionOfPerYear(double lng, double lat){
    	if (GeoNameUtils.lngLatCheck(lng, lat)) {
    		Grid grid = findGrid.find(lng, lat, gpcpGrid);
        	List<Precipitation> precipitations = precipitationDao.getAll(grid.getLongitude(), grid.getLatitude());
        	List<Double> res = new ArrayList<>();
        	for(int i = 0; i < TOTAL_YEAR; i++){
        		int index = 12 * i;
        		double pre = 0;
        		for(int j = 0; j < 12; j++){
        			Precipitation precipitation = precipitations.get(index + j);
            		pre += precipitation.getPrecipitation() * DateUtils.daysOfMonth(precipitation.getYear(), precipitation.getMonth());
        		}
        		res.add(pre);
        	}
        	return res;
		}
    	log.error("经纬度参数错误：纬度：" + lat + "经度：" + lng);
    	return null;
    }
    
    /**
     * 12个月的月均降水总量(十年平均值)
     * @param lng
     * @param lat
     * @return
     */
    public List<Double> avPrecipitataionOfPerMonth(double lng, double lat){
    	if (GeoNameUtils.lngLatCheck(lng, lat)) {
    		Grid grid = findGrid.find(lng, lat, gpcpGrid);
        	List<Precipitation> precipitations = precipitationDao.getAll(grid.getLongitude(), grid.getLatitude());
        	double[] res = new double[12];
        	for(Precipitation precipitation : precipitations){
        		int index = (precipitation.getMonth() - 1) % 12;
//        		System.out.println("(" + precipitation.getMonth() + " " + index + ")");
        		res[index] += precipitation.getPrecipitation() * DateUtils.daysOfMonth(precipitation.getYear(), precipitation.getMonth());
        		
        	}
        	List<Double> avgRes = new ArrayList<>();
        	for(double r : res){
        		avgRes.add(r / TOTAL_YEAR);
        	}
        	return avgRes;
		}
		log.error("经纬度参数错误：纬度：" + lat + "经度：" + lng);
    	return null;
    }
    
    public String jizhong(double lng, double lat){
    	List<Double> avgPrecipitataionOfPerMonth = avPrecipitataionOfPerMonth(lng, lat);
    	if (avgPrecipitataionOfPerMonth != null) {
			double summerP = avSummerPrecipitation(lat, avgPrecipitataionOfPerMonth);
			double winterP = avWinterPrecipitation(lat, avgPrecipitataionOfPerMonth);
//			System.out.println(summerP + " " + winterP);
			if (summerP - winterP > JINGZHONG_THRESHOLD) {
				return "夏季";
			} else if (winterP - summerP > JINGZHONG_THRESHOLD) {
				return "冬季";
			}else {
				return "平均";
			}
		}
    	return null;
    }
    
    /**
     * 夏季月均降水量
     * @param lat
     * @param avgPrecipitataionOfPerMonth
     * @return
     */
    public double avSummerPrecipitation(double lat, List<Double> avgPrecipitataionOfPerMonth){
    	//北半球
    	if (lat >= 0) {
			return getTotPrecipitationOfMonths(avgPrecipitataionOfPerMonth, SUMMER_MONTH_NORTH);
		} else {
			return getTotPrecipitationOfMonths(avgPrecipitataionOfPerMonth, WINTER_MONTH_NORTH);
		}
    }
    
    /**
     * 冬季月均降水量
     * @param lat
     * @param avgPrecipitataionOfPerMonth
     * @return
     */
    public double avWinterPrecipitation(double lat, List<Double> avgPrecipitataionOfPerMonth){
    	//北半球
    	if (lat >= 0) {
			return getTotPrecipitationOfMonths(avgPrecipitataionOfPerMonth, WINTER_MONTH_NORTH);
		} else {
			return getTotPrecipitationOfMonths(avgPrecipitataionOfPerMonth, SUMMER_MONTH_NORTH);
		}
    }
    
    /**
     * 获得特定几个月份的降水总量
     * @param avgPrecipitataionOfPerMonth 12个月的降水量
     * @param months 几个月份
     * @return
     */
    public double getTotPrecipitationOfMonths(List<Double> avgPrecipitataionOfPerMonth, int[] months){
    	double res = 0;
    	for(int i = 0; i < months.length; i++)
    		res += avgPrecipitataionOfPerMonth.get(months[i] - 1);
    	return res;
    }
    
	
    private List<List<Grid>> gpcpGrid() {
        List<List<Grid>> res = new ArrayList<>();
        double lat = 91.25;
        for (int i = 0; i < 72; i++) {
            lat -= 2.5;
            double lng = -181.25;
            List<Grid> row = new ArrayList<>();
            for (int j = 0; j < 144; j++) {
                lng += 2.5;
                row.add(new Grid(lng, lat));
            }
            res.add(row);
        }
        return res;
    }
}
