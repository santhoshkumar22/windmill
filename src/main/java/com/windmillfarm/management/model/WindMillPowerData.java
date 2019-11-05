package com.windmillfarm.management.model;

import java.time.LocalDate;

public class WindMillPowerData {
	
	private LocalDate date;
	private String uniqueId;
	private Long sum;
	private Double avearge;
	private Integer maximum;
	private Integer minimum;	
	
	public WindMillPowerData(LocalDate date, String uniqueId, Long sum, Double avearge, Integer maximum,
			Integer minimum) {
		this.date = date;
		this.uniqueId = uniqueId;
		this.sum = sum;
		this.avearge = avearge;
		this.maximum = maximum;
		this.minimum = minimum;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public Long getSum() {
		return sum;
	}
	public void setSum(Long sum) {
		this.sum = sum;
	}
	public Double getAvearge() {
		return avearge;
	}
	public void setAvearge(Double avearge) {
		this.avearge = avearge;
	}
	public Integer getMaximum() {
		return maximum;
	}
	public void setMaximum(Integer maximum) {
		this.maximum = maximum;
	}
	public Integer getMinimum() {
		return minimum;
	}
	public void setMinimum(Integer minimum) {
		this.minimum = minimum;
	}
	
}
