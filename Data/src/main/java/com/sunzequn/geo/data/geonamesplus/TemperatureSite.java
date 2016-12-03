package com.sunzequn.geo.data.geonamesplus;

public class TemperatureSite {
	
	private int id;
	private String sheng;
	private String name;
	
	public TemperatureSite(int id, String sheng, String name) {
		super();
		this.id = id;
		this.sheng = sheng;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSheng() {
		return sheng;
	}
	public void setSheng(String sheng) {
		this.sheng = sheng;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "TemperatureSite [id=" + id + ", sheng=" + sheng + ", name=" + name + "]";
	}
	
}
