package com.carlncarl.spdb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.spatial.jts.mgeom.MCoordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlncarl.spdb.dao.Mapper;
import com.carlncarl.spdb.dao.PointDao;
import com.carlncarl.spdb.dao.TrailDao;
import com.carlncarl.spdb.dao.UserDao;
import com.carlncarl.spdb.exception.WarsawTrailsException;
import com.carlncarl.spdb.model.MainPoint;
import com.carlncarl.spdb.model.Trail;
import com.carlncarl.spdb.model.TrailPoint;
import com.carlncarl.spdb.model.User;
import com.carlncarl.spdb.model.dto.CoordinateDto;
import com.carlncarl.spdb.model.dto.MainPointDto;
import com.carlncarl.spdb.model.dto.PointDto;
import com.carlncarl.spdb.model.dto.TrailDto;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequenceFactory;

@Service
public class TrailServiceIImpl implements TrailService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private TrailDao trailDao;

	@Autowired
	private PointDao pointDao;

	@Override
	public List<TrailDto> getTrailsByDate() {
		List<TrailDto> trails = trailDao.getTrailsByDateAdd();
		

		return trails;
	}

	@Override
	public List<TrailDto> getTrailsByRate() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	@Override
	public TrailDto save(TrailDto trailDto) throws WarsawTrailsException {
		trailDto = trailDao.save(trailDto);
		return trailDto;
	}

	@Override
	public TrailDto update(TrailDto trail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(TrailDto trail) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TrailDto getTrailById(Long id) {
		TrailDto trail = trailDao.getTrailById(id);
		
		
		return trail;
	}

	@Override
	public List<MainPointDto> getNearPoints(double latitude, double longitude, Long distance) {
		List<MainPointDto> points = trailDao.getNearPoints(latitude,longitude,distance);
		return points;
	}

}
