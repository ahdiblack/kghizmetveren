package com.yaser.data.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class TblPoiAdministrator implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name="administrator_id")
	private TblSubscriber tblSubscriber;
	@OneToOne
	@JoinColumn(name="poi_id")
	@Id
	private TblPoi tblPoi;
	private Boolean isPrimary;
	private short status;
	
	public TblPoiAdministrator() {
	}


	public TblPoi getTblPoi() {
		return this.tblPoi;
	}

	public void setTblPoi(TblPoi tblPoi) {
		this.tblPoi = tblPoi;
	}

	public Boolean getIsPrimary() {
		return this.isPrimary;
	}

	public void setIsPrimary(Boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}


	public TblSubscriber getTblSubscriber() {
		return tblSubscriber;
	}


	public void setTblSubscriber(TblSubscriber tblSubscriber) {
		this.tblSubscriber = tblSubscriber;
	}

}
