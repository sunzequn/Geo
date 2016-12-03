package com.sunzequn.geo.data.geonamesplus;

/**
 * @author jjyu
 * Created on 2016/11/19
 * 新的洋流实例对象
 */

public class OceanNew {

	// 实例库中所有逗号都为中文逗号，处理时所有分隔逗号都为英文逗号
	private int id;
	private String name; 			// 官方名称
	private String season; 			// 实例对应季节：夏季、冬季、四季
	private String propertyType; 	// 性质：寒、暖流
	private String geneticType;		// 成因：风海流、密度流、补偿流
	private String longitude; 		// 经度：-30.01，45.18
	private String latitude;		// 纬度：-30.01，30.18
	private String direction;		// 流向：由*向*，*时针
	private String passArea;		// 流经海/地域：#（洋）*部
	private String coastalArea;		// 沿岸陆地：#洲#岸，*国*部，*国*部……
	private String prevailingWind;	// 盛行风：*风
	private String intersection;	// 交汇情况：a|b|……
	private String coastalClimate;	// 沿岸气候：*（地区），*（气候）；
	private String fishery;			// 渔场：秘鲁渔场，秘鲁寒流是上升流；北海道渔场，日本暖流和千岛寒流交汇
	private String iceberg;			// 冰山：是/否
	
	public OceanNew() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getGeneticType() {
		return geneticType;
	}

	public void setGeneticType(String geneticType) {
		this.geneticType = geneticType;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getPassArea() {
		return passArea;
	}

	public void setPassArea(String passArea) {
		this.passArea = passArea;
	}

	public String getCoastalArea() {
		return coastalArea;
	}

	public void setCoastalArea(String coastalArea) {
		this.coastalArea = coastalArea;
	}

	public String getPrevailingWind() {
		return prevailingWind;
	}

	public void setPrevailingWind(String prevailingWind) {
		this.prevailingWind = prevailingWind;
	}

	public String getIntersection() {
		return intersection;
	}

	public void setIntersection(String intersection) {
		this.intersection = intersection;
	}

	public String getCoastalClimate() {
		return coastalClimate;
	}

	public void setCoastalClimate(String coastalClimate) {
		this.coastalClimate = coastalClimate;
	}

	public String getFishery() {
		return fishery;
	}

	public void setFishery(String fishery) {
		this.fishery = fishery;
	}

	public String getIceberg() {
		return iceberg;
	}

	public void setIceberg(String iceberg) {
		this.iceberg = iceberg;
	}

	@Override
	public String toString() {
		return "OceanCurrent [id=" + id + ", name=" + name + ", season=" + season + ", propertyType=" + propertyType
				+ ", geneticType=" + geneticType + ", longtitude=" + longitude + ", latitude" + latitude 
				+ ", direction=" + direction + ", passArea=" + passArea +", coastalArea=" + coastalArea
				+ ", prevailingWind=" + prevailingWind + ", intersection=" + intersection + ", coastalClimate=" 
				+ coastalClimate + ", fishery=" + fishery + ", iceberg=" + iceberg +"]";
	}

}
