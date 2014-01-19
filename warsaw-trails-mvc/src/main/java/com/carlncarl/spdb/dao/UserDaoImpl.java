package com.carlncarl.spdb.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.carlncarl.spdb.exception.WarsawTrailsException;
import com.carlncarl.spdb.model.MainPoint;
import com.carlncarl.spdb.model.PointRate;
import com.carlncarl.spdb.model.Trail;
import com.carlncarl.spdb.model.TrailRate;
import com.carlncarl.spdb.model.User;
import com.carlncarl.spdb.model.dto.PointRateDto;
import com.carlncarl.spdb.model.dto.TrailRateDto;
import com.carlncarl.spdb.model.dto.UserDto;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public UserDto getUserByLoginAndPassword(String login, String password) {
		UserDto dto = null;
		Session session = sessionFactory.openSession();
		Query query = session
				.createQuery(
						"from User where login = :login and password = :password")
				.setString("login", login).setString("password", password);
		User user = (User) query.uniqueResult();
		dto = Mapper.toUserDto(user);
		session.close();
		return dto;
	}

	@Override
	@Transactional
	public UserDto save(User user) throws WarsawTrailsException {
		UserDto dto = null;
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(user);
		dto = Mapper.getUserFromDao(user);
		try {
			session.getTransaction().commit();
		} catch (ConstraintViolationException e) {
			session.getTransaction().rollback();
			throw new WarsawTrailsException(
					WarsawTrailsException.ERROR_EXISTING_USER, null);
		}

		session.close();
		return dto;
	}

	@Override
	public boolean delete(User user) {
		Session session = sessionFactory.openSession();
		session.delete(user);
		session.close();
		return false;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public User getUserById(Long id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		User user = (User) session.get(User.class, id);

		session.getTransaction().commit();
		session.close();
		return user;

	}

	@Override
	public User update(User user, Trail trail) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		User u = (User) session.get(User.class, user.getId());
		u.getUserTrails().add(trail);
		session.update(u);

		session.getTransaction().commit();
		session.close();
		return user;
	}

	@Override
	public String voteTrail(TrailRateDto rate) {
		String status = "OK";
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		User user = (User) session.load(User.class, rate.getUserId());
		Trail trail = (Trail) session.get(Trail.class, rate.getTrailId());
		TrailRate dRate = new TrailRate();
		dRate.setUser(user);
		dRate.setTrail(trail);
		dRate.setRate(rate.getRate());
		dRate.setComment(rate.getComment());
		trail.getRates().add(dRate);
		session.update(trail);
		session.getTransaction().commit();
		session.close();
		return status;
	}

	@Override
	public String votePoint(PointRateDto rate) {
		String status = "OK";
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		User user = (User) session.load(User.class, rate.getUserId());
		MainPoint point = (MainPoint) session.get(MainPoint.class,
				rate.getMainPointId());
		PointRate dRate = new PointRate();
		dRate.setUser(user);
		dRate.setPoint(point);
		dRate.setRate(rate.getRate());
		dRate.setComment(rate.getComment());
		point.getRates().add(dRate);
		
		session.update(point);
		session.getTransaction().commit();
		session.close();
		return status;
	}

}
