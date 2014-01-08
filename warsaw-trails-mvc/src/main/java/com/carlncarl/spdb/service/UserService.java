package com.carlncarl.spdb.service;

import com.carlncarl.spdb.exception.WarsawTrailsException;
import com.carlncarl.spdb.model.User;

public interface UserService {

	public User login(String login, String password);
	public User register(String login, String password) throws WarsawTrailsException;
}
