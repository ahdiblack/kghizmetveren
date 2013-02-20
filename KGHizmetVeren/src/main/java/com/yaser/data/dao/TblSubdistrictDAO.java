package com.yaser.data.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.yaser.data.config.AbstractJpaDAO;
import com.yaser.data.model.TblSubdistrict;

@Repository
@Component
public class TblSubdistrictDAO extends AbstractJpaDAO<TblSubdistrict> {

	@PersistenceContext
	EntityManager entityManager;
	
	public TblSubdistrictDAO() {
		setClazz(TblSubdistrict.class);
	}

	@SuppressWarnings("unchecked")
	public List<TblSubdistrict> getByDistrictId(Integer districtID) {
		Query query = entityManager.createQuery("from TblSubdistrict where district.districtId = " + districtID);
		return query.getResultList();
	}
	
	
}
