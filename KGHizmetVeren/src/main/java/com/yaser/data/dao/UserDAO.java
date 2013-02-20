package com.yaser.data.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.yaser.data.config.AbstractJpaDAO;
import com.yaser.data.model.User;

@Repository
@Component
public class UserDAO extends AbstractJpaDAO<User> {

	@PersistenceContext
	EntityManager entityManager;
	
	public UserDAO() {
		setClazz(User.class);
	}
	
	@SuppressWarnings("unchecked")
	public User findByFBId(String fbId) {
		List<User> resultList = entityManager.createQuery("from " + User.class.getName()+ " where fb_id = :fbId")
			.setParameter("fbId", fbId).getResultList();
		if (resultList.isEmpty()) {
			return null;
		} else {
			return resultList.get(0);
		}
	}
	
}
