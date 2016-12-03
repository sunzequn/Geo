package com.sunzequn.geo.data.geonamesplus;

public class ExtZh {

	private String id;
	private String name;
	private String alternatenames;
	private double latitude;
	private double longitude;
	
	public ExtZh() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlternatenames() {
		return alternatenames;
	}

	public void setAlternatenames(String alternatenames) {
		this.alternatenames = alternatenames;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "ExtZh [id=" + id + ", name=" + name + ", alternatenames=" + alternatenames + ", latitude=" + latitude
				+ ", longitude=" + longitude + "]";
	}
	
	
}
