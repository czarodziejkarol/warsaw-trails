package com.carlncarl.spdb.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5512964569924682307L;

	private Long id;
	
	private String login;

	private String password;
	
	private List<Trail> userTrails;
	
	private List<TrailRate> userTrailRates;

	private List<PointRate> userPointRates;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Trail> getUserTrails() {
		return userTrails;
	}

	public void setUserTrails(List<Trail> userTrails) {
		this.userTrails = userTrails;
	}

	public List<TrailRate> getUserTrailRates() {
		return userTrailRates;
	}

	public void setUserTrailRates(List<TrailRate> userTrailRates) {
		this.userTrailRates = userTrailRates;
	}

	public List<PointRate> getUserPointRates() {
		return userPointRates;
	}

	public void setUserPointRates(List<PointRate> userPointRates) {
		this.userPointRates = userPointRates;
	}
}
