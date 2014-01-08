package com.carlncarl.spdb.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.carlncarl.spdb.model.User;
import com.vividsolutions.jts.geom.LineString;

public class TrailDto implements Serializable{

	private static final long serialVersionUID = -5671157372358596748L;
	
private Long id;
	
	private String name;
	
	private String description;
	
	private User creator;
	
	private Date dateAdd;
	
	private LineString road;
	
	private Double rate;
	
	private List<TrailPointDto> points;

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

	public List<TrailPointDto> getPoints() {
		return points;
	}

	public void setPoints(List<TrailPointDto> points) {
		this.points = points;
	}

	public Date getDateAdd() {
		return dateAdd;
	}

	public void setDateAdd(Date dateAdd) {
		this.dateAdd = dateAdd;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

}
