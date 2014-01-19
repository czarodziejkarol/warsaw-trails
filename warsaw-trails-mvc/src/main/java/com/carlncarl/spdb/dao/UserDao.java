package com.carlncarl.spdb.dao;

import com.carlncarl.spdb.exception.WarsawTrailsException;
import com.carlncarl.spdb.model.User;

public interface UserDao {
	
	public User getUserByLoginAndPassword(String login, String password);
	public User save(User user) throws WarsawTrailsException;
	public User update(User user);
	public boolean delete(User user);
	public User getUserById(Long id);
	
}
