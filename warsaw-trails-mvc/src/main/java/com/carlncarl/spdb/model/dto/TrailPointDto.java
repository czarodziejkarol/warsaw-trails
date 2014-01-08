package com.carlncarl.spdb.model.dto;

import java.io.Serializable;

public class TrailPointDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8992293287364811836L;

	private Long id;

	private String description;

	private MainPointDto point;

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

	public MainPointDto getPoint() {
		return point;
	}

	public void setPoint(MainPointDto point) {
		this.point = point;
	}
}
