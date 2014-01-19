package com.carlncarl.spdb.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.vividsolutions.jts.geom.LineString;

public class Trail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2399047544023811873L;

	private Long id;
	
	private String name;
	
	private String description;
	
	private User creator;
	
	private Date dateAdd;
	
	private LineString road;
	
	private String type;
	
	private Date startTime;
	
	private Date endTime;
	
	private List<TrailRate> rates;
	
	private List<TrailPoint> points;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public LineString getRoad() {
		return road;
	}

	public void setRoad(LineString road) {
		this.road = road;
	}

	public List<TrailRate> getRates() {
		return rates;
	}

	public void setRates(List<TrailRate> rates) {
		this.rates = rates;
	}

	public List<TrailPoint> getPoints() {
		return points;
	}

	public void setPoints(List<TrailPoint> points) {
		this.points = points;
	}

	public Date getDateAdd() {
		return dateAdd;
	}

	public void setDateAdd(Date dateAdd) {
		this.dateAdd = dateAdd;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
