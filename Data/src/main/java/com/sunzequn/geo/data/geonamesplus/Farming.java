package com.sunzequn.geo.data.geonamesplus;

public class Farming {
	
	private int id;
	private String type;
	private String lat;
	private String lng;
	private String notes;
	
	public Farming() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "OceanCurrent [id=" + id + ", type=" + type + ", lat=" + lat + ", lng=" + lng + ", notes=" + notes + "]";
	}

}
