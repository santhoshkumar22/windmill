package com.windmillfarm.management.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="WINDMILL_POWER")
public class WindMillPowerEntity {
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
     
    @Column(name="windmill_uid", length=16, nullable=false)
    private String windmillUniqueId;
     
    @Column(name="generated_date", nullable=false)
    private LocalDate generatedDate;
    
    @Column(name="power")
    private Integer power;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWindmillUniqueId() {
		return windmillUniqueId;
	}

	public void setWindmillUniqueId(String windmillUniqueId) {
		this.windmillUniqueId = windmillUniqueId;
	}

	public LocalDate getGeneratedDate() {
		return generatedDate;
	}

	public void setGeneratedDate(LocalDate generatedDate) {
		this.generatedDate = generatedDate;
	}

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}    
    
}