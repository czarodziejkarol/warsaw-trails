package com.carlncarl.spdb.android.dto;

import java.io.Serializable;

public class Point implements Serializable{

	private static final long serialVersionUID = 6409111065367562492L;
	private String name;
	private String description;
	private double longitude;
	private double latitude;
	
	
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
}
