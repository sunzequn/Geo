package com.sunzequn.geo.data.geonamesplus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GeonameHandler {
	
	private static final String GN_PREFIX = "gn:";
	private GeonameDao geonameDao = new GeonameDao();
	private static final Log log = LogFactory.getLog(GeonameHandler.class);

	public List<String> getNearby(int id){
		List<Integer> subjectNearby = geonameDao.getSubjectNearby(id);
		List<Integer> objectNearby = geonameDao.getObjectNearby(id);
		Set<Integer> nearby = new HashSet<Integer>();
		if (subjectNearby != null) {
			nearby.addAll(subjectNearby);
		}
		if (objectNearby != null) {
			nearby.addAll(objectNearby);
		}
		if (nearby.size() > 0) {
			List<String> names = new ArrayList<>();
			for(int geonameid : nearby){
				String zhName = getZhNameById(geonameid);
				if (zhName != null) {
					names.add(zhName);
				}
			}
			return names.size() == 0 ? null : names;
		}
		return null;
	}
	
    
    public String getZhNameById(int id){
//    	EL el = new EL();
//    	return el.geonamesID2Name(String.valueOf(id));
		return null;
    }
    
	
	
	public static int parseGnId(String uri){
		try {
			return Integer.parseInt(uri.trim().replace(GN_PREFIX, ""));
		} catch (Exception e) {
			log.error("Uri:" + uri + "的格式错误");
			return -1;
		}
	}
	
	public static String parseGnQId(String uri){
		try {
			return uri.trim().replace(GN_PREFIX, "");
		} catch (Exception e) {
			log.error("Uri:" + uri + "的格式错误");
			return null;
		}
	}
	
	public static Geoname getGeonames(String gn){
		if (!gn.contains("Q")) {
			GeonameDao geonameDao = new GeonameDao();
			return geonameDao.getById(parseGnId(gn));
		}
		return null;
	}
	
	public static ExtZh getExtZh(String gn){
		ExtZhDao extZhDao = new ExtZhDao();
		return extZhDao.getById(parseGnQId(gn));
	}

}
