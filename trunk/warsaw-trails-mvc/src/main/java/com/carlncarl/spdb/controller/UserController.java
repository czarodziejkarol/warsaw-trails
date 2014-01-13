package com.carlncarl.spdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.carlncarl.spdb.exception.WarsawTrailsException;
import com.carlncarl.spdb.model.User;
import com.carlncarl.spdb.model.dto.AbstractDto;
import com.carlncarl.spdb.model.dto.UserDto;
import com.carlncarl.spdb.service.UserService;

@Controller
@RequestMapping("api")
public class UserController {
	
	UserService userService;
	
	@Autowired
	public  UserController(UserService userService){
		this.userService = userService;
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	@ResponseBody
	public User login(String login, String password) {
		return userService.login(login, password);
	}
	
	@RequestMapping(value="register", method=RequestMethod.GET)
	@ResponseBody
	public UserDto register(String login, String password) {
		
		UserDto  retValue  = null; ;
		User  u;
		try {
			u = userService.register(login, password);
			retValue = UserDto.getUserFromDao(u);
		} catch (WarsawTrailsException e) {
			retValue = new UserDto(null);
			retValue.setErrorCode(e.getErrorCode());
			retValue.setErrorDesc(e.getErrorDesc());
		}
		return retValue;
	}
	
	
	
	
}
