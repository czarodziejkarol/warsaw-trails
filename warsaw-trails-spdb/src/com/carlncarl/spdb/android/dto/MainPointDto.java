package com.carlncarl.spdb.android.dto;

import java.io.Serializable;

public class MainPointDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4377834912450489076L;

	private Long id;

	private String name;

	private String description;

	private String poiReference; // https://developers.google.com/places/documentation/details

	private CoordinateDto coordinate;
	
	private Double rate;
	
	public MainPointDto() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
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

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public CoordinateDto getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(CoordinateDto coordinate) {
		this.coordinate = coordinate;
	}

}
