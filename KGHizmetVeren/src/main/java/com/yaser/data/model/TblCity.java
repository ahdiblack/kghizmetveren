package com.yaser.data.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class TblCity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int cityId;
	private String cityName;
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<TblDistrict> tblDistricts;


	public int getCityId() {
		return this.cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Set<TblDistrict> getTblDistricts() {
		return this.tblDistricts;
	}

	public void setTblDistricts(Set<TblDistrict> tblDistricts) {
		this.tblDistricts = tblDistricts;
	}

}
