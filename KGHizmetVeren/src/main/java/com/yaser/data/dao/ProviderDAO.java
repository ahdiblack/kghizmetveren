package com.yaser.data.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.yaser.data.config.AbstractJpaDAO;
import com.yaser.data.model.Provider;

@Repository
@Component
public class ProviderDAO extends AbstractJpaDAO<Provider> {

	@PersistenceContext
	EntityManager entityManager;
	
	public ProviderDAO() {
		setClazz(Provider.class);
	}
	
	
}
