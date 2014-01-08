package com.carlncarl.spdb.dao;

import com.carlncarl.spdb.exception.WarsawTrailsException;
import com.carlncarl.spdb.model.User;

public interface UserDao {
	
	public User getUserByLoginAndPassword(String login, String password);
	public User save(User user) throws WarsawTrailsException;
	public boolean delete(User user);
	
}
