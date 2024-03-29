package com.windmillfarm.management.model;

public class Location {

	private String longitude;
	private String latitude;	
	
	public Location(String longitude, String latitude){
		this.longitude=longitude;
		this.latitude=latitude;
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

	@Override
	public String toString() {
		return ("Loacation:[ Longitude "+longitude+ " Latitude: "+latitude);
	}
}
