package com.sunzequn.geo.data.geonamesplus;


import java.util.ArrayList;
import java.util.List;

//import com.sun.org.apache.bcel.internal.generic.NEW;

public class ClimateHandler {
	
	private static final double RE_YARE_THRESHOLD = 23.5;
	private static final double YARE_WEN_THRESHOLD = 40.0;
	private static final double WEN_HAN_THRESHOLD = 66.5;
    private FindGrid findGrid = new FindGrid();
    private KoppenDao koppenDao = new KoppenDao();
    private KoppenMappingDao koppenMappingDao = new KoppenMappingDao();
    private List<List<Grid>> koppenGrid = koppenGrid();
    private OceanCurrentDao oceanCurrentDao = new OceanCurrentDao();
    private OceanNewDao oceanNewDao = new OceanNewDao();
    private ChinaClimateFactorDao chinaClimateFactorDao = new ChinaClimateFactorDao();
    private FarmingDao farmingDao = new FarmingDao();
    
    public double nianJunQiWen(String name){
    	int id = TemperatureSiteInit.getId(name);
    	if (id != -1) {
    		List<ChinaClimateFactor> factors = chinaClimateFactorDao.getByName(id);
    		if (factors != null) {
				double temp = 0;
				for(ChinaClimateFactor factor : factors){
					temp += factor.getV12001();
				}
				return temp / factors.size() / 10;
			}
		}
    	return -1;
    }
    
    public String yinDuYang(double lng, double lat, String season){
    	double latMin = 0;
    	double latMax = 25;
    	double lngMin = 38;
    	double lngMax = 65;
    	if (lng > lngMin && lng <= lngMax && lat > latMin && lat <= latMax) {
			if (season.equals("夏季")) {
				return "寒流";
			} else if (season.equals("冬季")) {
				return "暖流";
			}
		}
    	return "无";
    }
    
    public String getFarming(double lng, double lat){
    	List<Farming> farmings = farmingDao.getAll();
    	for(Farming farming : farmings){
    		if (isMatched(lng, farming.getLng()) && isMatched(lat, farming.getLat())) {
				return farming.getType();
			}
    	}
    	return null;
    }
    
    public String getYangLiu(double lng, double lat){
    	List<OceanCurrent> currents = oceanCurrentDao.getAll();
    	for(OceanCurrent current : currents){
    		if (isMatched(lng, current.getLng()) && isMatched(lat, current.getLat())) {
				return current.getType();
			}
    	}
    	return "无";
    }
    
    public String getYangLiuNew(double lng, double lat, String season){
    	if(season == null)
    		season = "四季";
    	List<OceanNew> currents = oceanNewDao.getAll();
    	for(OceanNew current : currents){
    		if (isMatched(lng, current.getLongitude()) && isMatched(lat, current.getLatitude()) && season.equals(current.getSeason())) {
				return current.toString();
			}
    	}
    	return "无";
    }
    
    public String getReLiangDai(double lat){
    	lat = Math.abs(lat);
    	if (lat <= RE_YARE_THRESHOLD) {
			return "热带";
		} else if (lat <= YARE_WEN_THRESHOLD ) {
			return "亚热带";
		} else if (lat <= WEN_HAN_THRESHOLD) {
			return "温带";
		} else {
			return "寒带";
		}
    }
    

    /**
     * 根据经纬度获得Koppen气候类型
     *
     * @param lng
     * @param lat
     * @return
     */
    public String getKoppenTypeByLntLat(double lng, double lat) {
        Grid grid = findGrid.find(lng, lat, koppenGrid);
        System.out.println("获得最近的点：" + grid);
        if (grid != null) {
            Koppen koppen = koppenDao.getByLngLat(grid.getLongitude(), grid.getLatitude());
            if (koppen != null) {
                return koppen.getType();
            }
        }
        return null;
    }
    
    public String[] getZhongxueTypeByLntLat(double lng, double lat) {
        Grid grid = findGrid.find(lng, lat, koppenGrid);
        if (grid != null) {
            Koppen koppen = koppenDao.getByLngLat(grid.getLongitude(), grid.getLatitude());
            if (koppen != null) {
                List<KoppenMapping> koppenMappings = koppenMappingDao.getByKoppenType(koppen.getType());
                for(int i = 1; i < koppenMappings.size(); i++){
                	if (isKoppenMatched(lng, lat, koppenMappings.get(i))) {
						return new String[]{koppen.getType(), koppenMappings.get(i).getZhongxuetype()};
					}
                }
                return new String[]{koppen.getType(), koppenMappings.get(0).getZhongxuetype()};
            }
        }
        return null;
    }
    
    private boolean isKoppenMatched(double lng, double lat, KoppenMapping koppenMapping){
    	String latconstraint = koppenMapping.getLatconstraint();
    	String lngconstraint = koppenMapping.getLngconstraint();
    	if (!isEmpty(latconstraint)) {
			if (!isMatched(lat, latconstraint)) {
				return false;
			}
		}
    	if (!isEmpty(lngconstraint)) {
			if (!isMatched(lng, lngconstraint)) {
				return false;
			}
		}
    	return true;
    }
    
    private boolean isMatched(double d, String constraint){
    	String[] values = constraint.split(",");
		double min = Double.parseDouble(values[0]);
		double max = Double.parseDouble(values[1]);
		if (d <= max && d > min) {
			return true;
		}
		return false;
    }
    
    private boolean isEmpty(String string) {
    	string = string.trim();
		return (string == null || string.equals("") || string == "");
	}
    
    private List<List<Grid>> koppenGrid() {
        List<Koppen> koppens = koppenDao.getAll();
        List<List<Grid>> res = new ArrayList<>();
        List<Grid> row = new ArrayList<>();
        double latFlag = -9999;
        for (Koppen koppen : koppens) {
            if (koppen.getLatitude() != latFlag) {
                if (latFlag > -9999) {
                    res.add(row);
                }
                row = new ArrayList<>();
            }
            row.add(new Grid(koppen.getLongitude(), koppen.getLatitude()));
            latFlag = koppen.getLatitude();
        }
        res.add(row);
        return res;
    }
    
}
