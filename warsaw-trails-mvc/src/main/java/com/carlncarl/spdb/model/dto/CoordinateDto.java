package com.carlncarl.spdb.model.dto;

import java.io.Serializable;

public class CoordinateDto implements Serializable{

	private static final long serialVersionUID = -2929506129468348140L;

	private double latitude;
	
	private double longitude;

	public CoordinateDto (){
		
	}
	
	public CoordinateDto(double latitude, double longitude){
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
