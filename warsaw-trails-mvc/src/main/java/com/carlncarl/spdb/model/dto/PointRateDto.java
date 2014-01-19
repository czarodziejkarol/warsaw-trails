package com.carlncarl.spdb.model.dto;

import java.io.Serializable;

public class PointRateDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2813342655192150925L;

	private Long id;
	private Long userId;
	private String user;
	private Long mainPointId;
	private Integer rate;
	private String comment;
	
	public PointRateDto() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMainPointId() {
		return mainPointId;
	}

	public void setMainPointId(Long mainPointId) {
		this.mainPointId = mainPointId;
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
