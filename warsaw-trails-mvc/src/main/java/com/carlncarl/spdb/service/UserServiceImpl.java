package com.carlncarl.spdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlncarl.spdb.dao.UserDao;
import com.carlncarl.spdb.exception.WarsawTrailsException;
import com.carlncarl.spdb.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public User login(String login, String password) {
		User user = userDao.getUserByLoginAndPassword(login, password);
		return user;
	}
	
	@Override
	public User register(String login, String password) throws WarsawTrailsException {
		User user = new User();
		user.setLogin(login);	
		user.setPassword(password);
		user = userDao.save(user);
		return user;
	}
	

}
