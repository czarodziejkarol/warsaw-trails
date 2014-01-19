package com.carlncarl.spdb.model.dto;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3142201399700819065L;

	private Long id;

	private String login;
	
	private List<TrailRateDto> trailRates;
	
	private List<PointRateDto> pointRates;
	
	public UserDto(){
		
	}
	
	public UserDto(Long id, String login){
		this.id = id;
		this.login = login;
	}

	public UserDto(String login) {
		this.login = login;
	}

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

	public List<TrailRateDto> getTrailRates() {
		return trailRates;
	}

	public void setTrailRates(List<TrailRateDto> trailRates) {
		this.trailRates = trailRates;
	}

	public List<PointRateDto> getPointRates() {
		return pointRates;
	}

	public void setPointRates(List<PointRateDto> pointRates) {
		this.pointRates = pointRates;
	}
}
