package com.carlncarl.spdb.model;

import java.io.Serializable;
import java.util.List;

import com.vividsolutions.jts.geom.Point;

public class MainPoint implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7523211134955390549L;

	private Long id;
	
	private String name;
	
	private String description;
	
	private String poiReference; //https://developers.google.com/places/documentation/details
	
	private Point point;
	
	private List<PointRate> rates;
	
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

	public List<PointRate> getRates() {
		return rates;
	}

	public void setRates(List<PointRate> rates) {
		this.rates = rates;
	}
}
