package com.yaser.data.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.yaser.data.config.AbstractJpaDAO;
import com.yaser.data.model.TblCategory;

@Repository
@Component
public class TblCategoryDAO extends AbstractJpaDAO<TblCategory> {

	@PersistenceContext
	EntityManager entityManager;
	
	public TblCategoryDAO() {
		setClazz(TblCategory.class);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TblCategory> getParentCategories() {
		Query query = entityManager.createQuery("from TblCategory tg where tg.parentId = :pid");
		query.setParameter("pid", 0);
		
		return query.getResultList();
	}
}
