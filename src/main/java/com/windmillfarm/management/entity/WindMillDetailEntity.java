package com.windmillfarm.management.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="WINDMILL_DETAILS")
public class WindMillDetailEntity {
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
     
    @Column(name="uid", length=16, nullable=false, unique=true)
    private String uniqueId;
    
    @Column(name="name",nullable=false)
    private String name;
    
    @Column(name="address")
    private String address;
    
    @Column(name="latitude")
    private String latitude;
    
    @Column(name="longitude")
    private String longitude;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}    
    
}