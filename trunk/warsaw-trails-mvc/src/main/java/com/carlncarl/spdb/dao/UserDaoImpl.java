package com.carlncarl.spdb.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.carlncarl.spdb.exception.WarsawTrailsException;
import com.carlncarl.spdb.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User getUserByLoginAndPassword(String login, String password) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(
						"from User where login = :login and password = :password")
				.setString("login", login).setString("password", password);
		User user = (User) query.uniqueResult();
		session.close();
		return user;
	}

	@Override
	@Transactional
	public User save(User user) throws WarsawTrailsException{
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(user);
		try {
			session.getTransaction().commit();
		} catch (ConstraintViolationException e) {
			session.getTransaction().rollback();
			throw new WarsawTrailsException(WarsawTrailsException.ERROR_EXISTING_USER, null);
		}
		
		session.close();
		return user;
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

}
