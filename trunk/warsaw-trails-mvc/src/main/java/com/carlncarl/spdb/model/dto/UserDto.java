package com.carlncarl.spdb.model.dto;

import java.io.Serializable;

import com.carlncarl.spdb.model.User;

public class UserDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3142201399700819065L;

	private Long id;

	private String login;
	
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

	public static UserDto getUserFromDao(User register) {
		return new UserDto(register.getId(), register.getLogin());
	}
}
