package com.yaser.data.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.yaser.data.config.AbstractJpaDAO;
import com.yaser.data.model.TblSubscriber;

@Repository
@Component
public class TblSubscriberDAO extends AbstractJpaDAO<TblSubscriber> {

	@PersistenceContext
	EntityManager entityManager;
	
	public TblSubscriberDAO() {
		setClazz(TblSubscriber.class);
	}
	
	@SuppressWarnings("unchecked")
	public TblSubscriber findByFBId(String fbId) {
		List<TblSubscriber> resultList = entityManager.createQuery("from " + TblSubscriber.class.getName()+ " where facebookId = :fbId")
			.setParameter("fbId", fbId).getResultList();
		if (resultList.isEmpty()) {
			return null;
		} else {
			return resultList.get(0);
		}
	}
	
}
