package com.carlncarl.spdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.carlncarl.spdb.exception.WarsawTrailsException;
import com.carlncarl.spdb.model.dto.MainPointDto;
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

	@RequestMapping(value = "new-trails", method = RequestMethod.GET)
	@ResponseBody
	public List<TrailDto> getNewTrails() {
		List<TrailDto> trails = trailService.getTrailsByDate();
		return trails;
	}

	@RequestMapping(value = "near-points", method = RequestMethod.GET)
	@ResponseBody
	public List<MainPointDto> getNearPoints(@RequestParam double latitude,
			@RequestParam double longitude, @RequestParam Long distance) {
		List<MainPointDto> points = trailService.getNearPoints(latitude,
				longitude, distance);
		return points;
	}

	@RequestMapping(value = "trail", method = RequestMethod.GET)
	@ResponseBody
	public TrailDto getNewTrails(@RequestParam Long id) {

		TrailDto trail = trailService.getTrailById(id);

		return trail;

	}

	@RequestMapping(value = "add-trail", method = RequestMethod.POST)
	@ResponseBody
	public TrailDto addTrail(@RequestBody TrailDto trailDto) {
		TrailDto retValue = new TrailDto();
		try {
			retValue = trailService.save(trailDto);
		} catch (WarsawTrailsException e) {
			e.printStackTrace();
		}
		return retValue;

	}
}
