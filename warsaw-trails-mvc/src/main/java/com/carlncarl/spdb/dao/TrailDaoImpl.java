package com.carlncarl.spdb.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.carlncarl.spdb.exception.WarsawTrailsException;
import com.carlncarl.spdb.model.Trail;

@Repository
public class TrailDaoImpl implements TrailDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Trail save(Trail trail) throws WarsawTrailsException {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Trail trailSaved = (Trail) session.save(trail);
		session.getTransaction().commit();
		
		return trailSaved;
	}

	@Override
	public boolean delete(Trail trail) {
		// TODO Auto-generated method stub
		return false;
	}

}
