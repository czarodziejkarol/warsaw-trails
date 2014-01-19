package com.carlncarl.spdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.carlncarl.spdb.exception.WarsawTrailsException;
import com.carlncarl.spdb.model.dto.PointRateDto;
import com.carlncarl.spdb.model.dto.TrailRateDto;
import com.carlncarl.spdb.model.dto.UserDto;
import com.carlncarl.spdb.service.UserService;

@Controller
@RequestMapping("api")
public class UserController {

	UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "register", method = RequestMethod.GET)
	@ResponseBody
	public UserDto register(String login, String password) {


		UserDto u = null;
		try {
			u = userService.register(login, password);

		} catch (WarsawTrailsException e) {
			u = userService.login(login, password);
		}

		return u;
	}
	
	@RequestMapping(value = "vote-trail", method = RequestMethod.POST)
	@ResponseBody
	public String voteTrail(@RequestBody TrailRateDto rate){
		String result = userService.voteTrail(rate);
		return result;
	}

	@RequestMapping(value = "vote-point", method = RequestMethod.POST)
	@ResponseBody
	public String voteTrail(@RequestBody PointRateDto rate){
		String result = userService.votePoint(rate);
		return result;
	}
}
