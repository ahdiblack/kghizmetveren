package com.yaser.data.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.yaser.data.config.AbstractJpaDAO;
import com.yaser.data.model.TblCity;

@Repository
@Component
public class TblCityDAO extends AbstractJpaDAO<TblCity> {

	@PersistenceContext
	EntityManager entityManager;
	
	public TblCityDAO() {
		setClazz(TblCity.class);
	}
	
}
