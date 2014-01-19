package com.carlncarl.spdb.android.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PointDto implements Serializable{

	private static final long serialVersionUID = 6409111065367562492L;
	
	private Long id;
	private String name;
	private String description;
	private String pointDescription;
	private String poiRef;
	private Long mainPointId;
	private double longitude;
	private double latitude;
	private Date startTime;
	private Date endTime;
	private Double rate;
	private List<PointRateDto> pointRates;
	
	public PointDto(){
		this.startTime = new Date();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	@Override
	public String toString() {
		return this.getName();
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPoiRef() {
		return poiRef;
	}

	public void setPoiRef(String poiRef) {
		this.poiRef = poiRef;
	}

	public Long getMainPointId() {
		return mainPointId;
	}

	public void setMainPointId(Long mainPointId) {
		this.mainPointId = mainPointId;
	}

	public String getPointDescription() {
		return pointDescription;
	}

	public void setPointDescription(String pointDescription) {
		this.pointDescription = pointDescription;
	}

	public List<PointRateDto> getPointRates() {
		return pointRates;
	}

	public void setPointRates(List<PointRateDto> pointRates) {
		this.pointRates = pointRates;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

}
