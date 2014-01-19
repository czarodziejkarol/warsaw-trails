package com.carlncarl.spdb.android.dto;

import java.io.Serializable;

public class TrailRateDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4468931612939293518L;

	
	private Long id;
	private Long userId;
	private String user;
	private Long trailId;
	private Integer rate;
	private String comment;
	
	public TrailRateDto() {
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getTrailId() {
		return trailId;
	}

	public void setTrailId(Long trailId) {
		this.trailId = trailId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}



}
