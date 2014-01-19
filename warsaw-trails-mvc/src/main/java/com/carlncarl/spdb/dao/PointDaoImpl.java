package com.carlncarl.spdb.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.carlncarl.spdb.model.MainPoint;

@Repository
public class PointDaoImpl implements PointDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public MainPoint getMainPointById(Long id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session
				.createQuery("select mp from MainPoint mp where mp.id = :parameter");
		query.setParameter("parameter", id);

		MainPoint point = (MainPoint) query.uniqueResult();

		session.getTransaction().commit();

		session.close();
		return point;
	}

	@Override
	@Transactional
	public MainPoint getMainPointByPoi(String poi) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session
				.createQuery("select mp from MainPoint mp where mp.poiReference = :parameter");
		query.setParameter("parameter", poi);

		MainPoint point = (MainPoint) query.uniqueResult();

		session.getTransaction().commit();

		session.close();
		return point;
	}

}
