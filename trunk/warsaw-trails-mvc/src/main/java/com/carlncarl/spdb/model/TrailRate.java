package com.carlncarl.spdb.model;

import java.io.Serializable;

public class TrailRate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5304761162371481642L;

	private Trail trail;
	
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

	public Trail getTrail() {
		return trail;
	}

	public void setTrail(Trail trail) {
		this.trail = trail;
	}

}
