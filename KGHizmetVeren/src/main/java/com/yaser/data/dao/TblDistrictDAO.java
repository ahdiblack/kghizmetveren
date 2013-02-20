package com.yaser.data.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.yaser.data.config.AbstractJpaDAO;
import com.yaser.data.model.TblDistrict;

@Repository
@Component
public class TblDistrictDAO extends AbstractJpaDAO<TblDistrict> {

	@PersistenceContext
	EntityManager entityManager;
	
	public TblDistrictDAO() {
		setClazz(TblDistrict.class);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TblDistrict> getByCityId(int cityId) {
//		
//		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//		CriteriaQuery<TblDistrict> crit = cb.createQuery(TblDistrict.class);
//		Root<TblDistrict> candidateRoot = crit.from(TblDistrict.class);
//		
//		Predicate nameEquals = cb.equal(candidateRoot.get("name"), "First");
//		crit.where(nameEquals);
//		candidateRoot.alias("p");
//		crit.select(candidateRoot);
		
		Query query = entityManager.createQuery("from TblDistrict where tblCity.cityId = " + cityId);
//		query.setParameter("id", cityId);
		return query.getResultList();
	}
	
}
