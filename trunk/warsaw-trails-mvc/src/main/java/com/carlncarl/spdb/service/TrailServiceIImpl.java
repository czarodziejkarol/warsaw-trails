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
		Trail trail = new Trail();
		User u = userDao.getUserById(trailDto.getCreator().getId());
		trail.setCreator(u);
		trail.setDateAdd(new Date());
		trail.setDescription(trailDto.getDescription());
		trail.setEndTime(trailDto.getEndTime());
		trail.setName(trailDto.getName());
		trail.setStartTime(trailDto.getStartTime());
		trail.setType(trailDto.getType());

		Coordinate[] coordinates = new Coordinate[trailDto.getPath().size()];
		for (int i = 0; i < trailDto.getPath().size(); i++) {
			CoordinateDto cDto = trailDto.getPath().get(i);
			coordinates[i] = new Coordinate(cDto.getLatitude(),
					cDto.getLongitude());
		}

		CoordinateSequence roadPoints = CoordinateArraySequenceFactory
				.instance().create(coordinates);
		LineString road = new LineString(roadPoints, new GeometryFactory());
		trail.setRoad(road);

		List<TrailPoint> points = new ArrayList<TrailPoint>();

		for (PointDto pointDto : trailDto.getPoints()) {

			TrailPoint tp = new TrailPoint();
			tp.setDescription(pointDto.getPointDescription());
			tp.setTrail(trail);
			tp.setEndTime(pointDto.getEndTime());
			tp.setStartTime(pointDto.getStartTime());
			
			
			MainPoint mainPoint = null;
			if (pointDto.getPoiRef() != null) {
				mainPoint = pointDao.getMainPointByPoi(pointDto.getPoiRef());
			} else if (pointDto.getMainPointId() != null) {
				mainPoint = pointDao
						.getMainPointById(pointDto.getMainPointId());
			} else {
				// tworzenie nowego main pointa
				mainPoint = new MainPoint();
				mainPoint.setDescription(pointDto.getDescription());
				mainPoint.setPoiReference(pointDto.getPoiRef());
				mainPoint.setPoint(new MCoordinate(pointDto.getLatitude(), pointDto.getLongitude()));

				mainPoint.setName(pointDto.getName());

			}
			tp.setPoint(mainPoint);
			
			points.add(tp);

		}

		trail.setPoints(points);
		
//		List<Trail> userTrails = new ArrayList<Trail>();
//		userTrails.add(trail);

//		u.setUserTrails(userTrails );

		//trail = trailDao.save(trail);
	//	userDao.save(u);
		userDao.update(u, trail);
		
		trailDto.setId(trail.getId());

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
