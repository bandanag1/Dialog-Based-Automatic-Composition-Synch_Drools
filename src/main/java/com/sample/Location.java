package com.sample;

public class Location {
	
	private String city;
	private String land;
	
	public Location() {
		
	}
	
	public Location(String city, String land) {
		this.city = city;
		this.land = land;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public String getLand() {
		return this.land;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public void setLand(String land) {
		this.land = land;
	}
	
}
