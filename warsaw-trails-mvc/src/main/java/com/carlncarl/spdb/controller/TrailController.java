package com.carlncarl.spdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.carlncarl.spdb.exception.WarsawTrailsException;
import com.carlncarl.spdb.model.dto.AbstractDto;
import com.carlncarl.spdb.model.dto.TrailDto;
import com.carlncarl.spdb.service.TrailService;

@Controller
@RequestMapping("api")
public class TrailController {
	
	private TrailService trailService;
	
	@Autowired
	public TrailController(TrailService trailService) {
		this.trailService = trailService;
	}
	
	@RequestMapping(value="new-trails", method=RequestMethod.POST)
	@ResponseBody
	public AbstractDto getNewTrails(){
		AbstractDto retValue = new AbstractDto();
		
		List<TrailDto> trails = trailService.getTrailsByDate();
		
		retValue.setObject(trails);
		
		return retValue;
		
	}

	@RequestMapping(value="add-trail", method=RequestMethod.POST)
	@ResponseBody
	public AbstractDto addTrail(TrailDto trailDto){
		AbstractDto retValue = new AbstractDto();
		
		TrailDto trail = null;
		try {
			trail = trailService.save(trailDto);
		} catch (WarsawTrailsException e) {
			retValue.setErrorCode(e.getErrorCode());
			retValue.setErrorDesc(e.getErrorDesc());
			e.printStackTrace();
		}
		retValue.setObject(trail);
		
		
		return retValue;
		
	}
}
