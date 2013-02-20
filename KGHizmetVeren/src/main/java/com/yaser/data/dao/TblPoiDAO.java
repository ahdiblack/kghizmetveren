package com.yaser.data.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.yaser.data.config.AbstractJpaDAO;
import com.yaser.data.model.TblPoi;

@Repository
@Component
public class TblPoiDAO extends AbstractJpaDAO<TblPoi> {

	@PersistenceContext
	EntityManager entityManager;
	
	public TblPoiDAO() {
		setClazz(TblPoi.class);
	}

	@SuppressWarnings("unchecked")
	public TblPoi findByAdminId(Integer subscriberId) {
		Query nativeQuery = entityManager.createNativeQuery("SELECT * FROM tbl_poi p where p.poi_id in (select poi_id from tbl_poi_administrator pa where pa.administrator_id = "+subscriberId+")", TblPoi.class);
		List<TblPoi> resultList = nativeQuery.getResultList();
		if (resultList.size() > 0) {
			return resultList.get(0);
		}
		return null;
	}
	
	
}
