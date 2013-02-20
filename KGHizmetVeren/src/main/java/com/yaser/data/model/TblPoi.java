package com.yaser.data.model;

//Generated Jul 22, 2012 7:33:02 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class TblPoi implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int poiId;
	
	@ManyToOne
	@JoinColumn(name="district_id")
	private TblDistrict tblDistrict;
	@ManyToOne
	@JoinColumn(name="subdistrict_id")
	private TblSubdistrict tblSubdistrict;
	private String uniqueIdentifier;
	private String address;
	@ManyToOne
	@JoinColumn(name="city_id")
	private TblCity city;
	private BigDecimal coordLat;
	private BigDecimal coordLong;
	private String info;
	private String phone;
	private Date dateAdded;
	private String poiName;
	private String profileImage;
	private String keywords;
	private String website;
	private String authorityEmail;
	private Integer category;
	
	public int getPoiId() {
		return poiId;
	}
	public void setPoiId(int poiId) {
		this.poiId = poiId;
	}
	public TblDistrict getTblDistrict() {
		return tblDistrict;
	}
	public void setTblDistrict(TblDistrict tblDistrict) {
		this.tblDistrict = tblDistrict;
	}
	public TblSubdistrict getTblSubdistrict() {
		return tblSubdistrict;
	}
	public void setTblSubdistrict(TblSubdistrict tblSubdistrict) {
		this.tblSubdistrict = tblSubdistrict;
	}
	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}
	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public BigDecimal getCoordLat() {
		return coordLat;
	}
	public void setCoordLat(BigDecimal coordLat) {
		this.coordLat = coordLat;
	}
	public BigDecimal getCoordLong() {
		return coordLong;
	}
	public void setCoordLong(BigDecimal coordLong) {
		this.coordLong = coordLong;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public String getPoiName() {
		return poiName;
	}
	public void setPoiName(String poiName) {
		this.poiName = poiName;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public TblCity getCity() {
		return city;
	}
	public void setCity(TblCity city) {
		this.city = city;
	}
	public String getAuthorityEmail() {
		return authorityEmail;
	}
	public void setAuthorityEmail(String authorityEmail) {
		this.authorityEmail = authorityEmail;
	}
}