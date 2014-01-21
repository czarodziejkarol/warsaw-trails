package com.carlncarl.spdb.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.spatial.jts.mgeom.MCoordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
import com.vividsolutions.jts.geom.impl.CoordinateArraySequenceFactory;

@Repository
public class TrailDaoImpl implements TrailDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public TrailDto save(TrailDto trailDto) throws WarsawTrailsException {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		
		
		Trail trail = new Trail();
		User u = (User) session.get(User.class, trailDto.getCreator().getId());
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
			if (pointDto.getMainPointId() != null) {
				mainPoint = (MainPoint) session.load(MainPoint.class, pointDto.getMainPointId());
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
	//	userDao.update(u, trail);
		
		
		
		u.getUserTrails().add(trail);
		session.saveOrUpdate(u);
		
		
		session.getTransaction().commit();
		session.close();
		trailDto.setId(trail.getId());
		
		return trailDto;
	}

	@Override
	public List<TrailDto> getTrailsByDateAdd() {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Query query = session
				.createQuery("select t from Trail t order by t.dateAdd DESC");
		List<Trail> trails = query.list();
		
		List<TrailDto> trailsDto = new ArrayList<TrailDto>();
		for (Trail trail : trails) {
			trailsDto.add(Mapper.toSimpleTrail(trail));
		}
		
		session.getTransaction().commit();
		session.close();
		return trailsDto;
	}

	@Override
	public boolean delete(Trail trail) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TrailDto getTrailById(Long id) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Trail trail = (Trail) session.get(Trail.class, id);

		TrailDto dto = Mapper.toTrailDto(trail);

		session.getTransaction().commit();
		session.close();

		return dto;
	}

	@Override
	public List<MainPointDto> getNearPoints(double latitude, double longitude,
			Long distance) {
		List<MainPointDto> pointsDto = new ArrayList<MainPointDto>();

		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("select p from MainPoint p");

		List<MainPoint> points = query.list();
		for (MainPoint mainPoint : points) {
			if (distFrom(mainPoint.getPoint().x, mainPoint.getPoint().y, latitude, longitude) < distance) {
				pointsDto.add(Mapper.toSimpleMainPointsDto(mainPoint));
			}
		}
		session.getTransaction().commit();
		session.close();

		return pointsDto;
	}

	public static double distFrom(double lat1, double lng1, double lat2,
			double lng2) {
		double earthRadius = 6371000 ;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double sindLat = Math.sin(dLat / 2);
		double sindLng = Math.sin(dLng / 2);
		double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
				* Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		return dist;
	}

}
