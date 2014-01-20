package com.carlncarl.spdb.service;

import com.carlncarl.spdb.exception.WarsawTrailsException;
import com.carlncarl.spdb.model.dto.PointRateDto;
import com.carlncarl.spdb.model.dto.TrailRateDto;
import com.carlncarl.spdb.model.dto.UserDto;

public interface UserService {

	public UserDto login(String login, String password);
	public UserDto register(String login, String password) throws WarsawTrailsException;
	public TrailRateDto voteTrail(TrailRateDto rate);
	public PointRateDto votePoint(PointRateDto rate);
}
