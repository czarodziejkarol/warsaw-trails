package com.carlncarl.spdb.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.carlncarl.spdb.exception.WarsawTrailsException;
import com.carlncarl.spdb.model.MainPoint;
import com.carlncarl.spdb.model.Trail;
import com.carlncarl.spdb.model.dto.MainPointDto;
import com.carlncarl.spdb.model.dto.TrailDto;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequenceFactory;

@Repository
public class TrailDaoImpl implements TrailDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public Trail save(Trail trail) throws WarsawTrailsException {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Long trailId = (Long) session.save(trail);
		session.getTransaction().commit();
		session.close();
		return trail;
	}

	@Override
	public List<Trail> getTrailsByDateAdd() {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Query query = session
				.createQuery("select t from Trail t order by t.dateAdd DESC");
		List<Trail> trails = query.list();

		session.getTransaction().commit();
		session.close();
		return trails;
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
		Query query = session.createQuery("select p from MainPoint p where dwithin(p.point, :center, :distance");
		Coordinate[] coors = new Coordinate[1];
		coors[0] = new Coordinate(latitude, longitude);
		
		CoordinateSequence coordinates = CoordinateArraySequenceFactory
				.instance().create(coors );
		Point point = new Point(coordinates , new GeometryFactory());
		query.setParameter("center", point);
		query.setParameter("distance", distance);
		List<MainPoint> points  = query.list();
		for (MainPoint mainPoint : points) {
			pointsDto.add(Mapper.toSimpleMainPointsDto(mainPoint));
		}
		session.getTransaction().commit();
		session.close();

		return pointsDto;
	}

}
