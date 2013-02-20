package com.yaser.data.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.yaser.data.config.AbstractJpaDAO;
import com.yaser.data.model.TblPoiAdministrator;

@Repository
@Component
public class TblPoiAdministratorDAO extends AbstractJpaDAO<TblPoiAdministrator> {

	@PersistenceContext
	EntityManager entityManager;
	
	public TblPoiAdministratorDAO() {
		setClazz(TblPoiAdministrator.class);
	}
	
	
}
