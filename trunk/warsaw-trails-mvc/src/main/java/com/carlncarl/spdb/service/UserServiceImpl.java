package com.carlncarl.spdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlncarl.spdb.dao.UserDao;
import com.carlncarl.spdb.exception.WarsawTrailsException;
import com.carlncarl.spdb.model.User;
import com.carlncarl.spdb.model.dto.PointRateDto;
import com.carlncarl.spdb.model.dto.TrailRateDto;
import com.carlncarl.spdb.model.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDto login(String login, String password) {
		UserDto user = userDao.getUserByLoginAndPassword(login, password);
		return user;
	}
	
	@Override
	public UserDto register(String login, String password) throws WarsawTrailsException {
		User user = new User();
		user.setLogin(login);	
		user.setPassword(password);
		UserDto dto  = userDao.save(user);
		return dto;
	}

	@Override
	public TrailRateDto voteTrail(TrailRateDto rate) {
		// TODO Auto-generated method stub
		return userDao.voteTrail(rate);
	}

	@Override
	public PointRateDto votePoint(PointRateDto rate) {
		// TODO Auto-generated method stub
		return userDao.votePoint(rate);
	}
	

}
