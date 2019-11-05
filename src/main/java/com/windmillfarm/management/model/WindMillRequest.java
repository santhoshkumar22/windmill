package com.windmillfarm.management.model;

public class WindMillRequest {
	
	private String uniqueId;
	private String name;
	private String address;
	private Location location;
	
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return ("WindMillRequest:[ uniqueId "+uniqueId+ " name: "+name +" address: "+address + " location: "+location);
	}
}
