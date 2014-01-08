package com.carlncarl.spdb.model.dto;

import java.io.Serializable;
import java.util.List;

import com.carlncarl.spdb.model.PointRate;
import com.vividsolutions.jts.geom.Point;

public class MainPointDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4377834912450489076L;

	private Long id;

	private String name;

	private String description;

	private String poiReference; // https://developers.google.com/places/documentation/details

	private Point point;
	
	private Double rate;

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

	public String getPoiReference() {
		return poiReference;
	}

	public void setPoiReference(String poiReference) {
		this.poiReference = poiReference;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

}
