package com.sunzequn.geo.data.geonamesplus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemperatureSiteInit {
	
    private static final String Gnext_TEMPERATURE_SITE = "data/gnext/temperature_site";
	private static Map<String, TemperatureSite> temperatureMap = new HashMap<String, TemperatureSite>();
	
	static{
		ReadUtils readUtils = new ReadUtils(Gnext_TEMPERATURE_SITE);
		List<String> lines = readUtils.readByLine();
		for(String line : lines){
			String[] datas = line.split(" ");
			int id = Integer.parseInt(datas[0]);
			String name = datas[1];
			String sheng = datas[2];
			temperatureMap.put(name, new TemperatureSite(id, sheng, name));
		}
	}
	
	public static int getId(String name){
		TemperatureSite site = temperatureMap.get(name);
		if (site != null) {
			return site.getId();
		}
		return -1;
	}
}
