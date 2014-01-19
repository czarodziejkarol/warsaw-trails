package com.carlncarl.spdb.dao;

import com.carlncarl.spdb.exception.WarsawTrailsException;
import com.carlncarl.spdb.model.User;
import com.carlncarl.spdb.model.dto.PointRateDto;
import com.carlncarl.spdb.model.dto.TrailRateDto;
import com.carlncarl.spdb.model.dto.UserDto;

public interface UserDao {
	
	public UserDto getUserByLoginAndPassword(String login, String password);
	public UserDto save(User user) throws WarsawTrailsException;
	public User update(User user);
	public boolean delete(User user);
	public User getUserById(Long id);
	public String voteTrail(TrailRateDto rate);
	public String votePoint(PointRateDto rate);
	
}
