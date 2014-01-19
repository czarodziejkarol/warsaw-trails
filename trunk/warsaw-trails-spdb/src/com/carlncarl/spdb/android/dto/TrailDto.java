package com.carlncarl.spdb.android.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class TrailDto implements Serializable {

	private static final long serialVersionUID = -5671157372358596748L;

	private Long id;
	private String name;
	private String description;
	private String type;
	private Date startTime;
	private Date endTime;
	private ArrayList<CoordinateDto> path;
	private ArrayList<PointDto> points;
	private UserDto creator;

	public TrailDto(){
		
	}
	
	public TrailDto(String name, String description, String type, UserDto creator) {
		id = null;
		this.name = name;
		this.description = description;
		this.type = type;
		this.points = new ArrayList<PointDto>();
		this.path = new ArrayList<CoordinateDto>();
		this.creator = creator;
		startTime = new Date();
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<PointDto> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<PointDto> points) {
		this.points = points;
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

	public ArrayList<CoordinateDto> getPath() {
		return path;
	}

	public void setPath(ArrayList<CoordinateDto> path) {
		this.path = path;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDto getCreator() {
		return creator;
	}

	public void setCreator(UserDto creator) {
		this.creator = creator;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
