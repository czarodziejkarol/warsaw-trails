package com.carlncarl.spdb.model;

import java.io.Serializable;

public class PointRate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4803327111389696260L;
	
	private MainPoint point;

	private Long id;

	private User user;

	private Integer rate;
	
	private String comment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	
	public MainPoint getPoint() {
		return point;
	}

	public void setPoint(MainPoint point) {
		this.point = point;
	}

}
