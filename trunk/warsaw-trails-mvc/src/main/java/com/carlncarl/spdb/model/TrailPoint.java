package com.carlncarl.spdb.model;

import java.io.Serializable;

public class TrailPoint implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -738272320491453024L;

	private Long id;
	
	private String description;
	
	private Trail trail;
	
	private MainPoint point;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Trail getTrail() {
		return trail;
	}

	public void setTrail(Trail trail) {
		this.trail = trail;
	}

	public MainPoint getPoint() {
		return point;
	}

	public void setPoint(MainPoint point) {
		this.point = point;
	}
}
