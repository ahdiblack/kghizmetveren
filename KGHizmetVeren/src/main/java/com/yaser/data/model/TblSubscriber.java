package com.yaser.data.model;


import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TblSubscriber implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer subscriberId;
	private String facebookId;
	private String twitterId;
	private String googlePlusId;
	private String name;
	private String surname;
	private Date birthdate;
	private Short subscriberType;
	private Date joinDate = Calendar.getInstance().getTime();
	private Integer primaryLocation;
	private Integer secondaryLocation;
	private String gender;
	private Integer activated;
	private String email;
	private String imageUrl;


	public Integer getSubscriberId() {
		return this.subscriberId;
	}

	public void setSubscriberId(Integer subscriberId) {
		this.subscriberId = subscriberId;
	}

	public String getFacebookId() {
		return this.facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getTwitterId() {
		return this.twitterId;
	}

	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}

	public String getGooglePlusId() {
		return this.googlePlusId;
	}

	public void setGooglePlusId(String googlePlusId) {
		this.googlePlusId = googlePlusId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Short getSubscriberType() {
		return this.subscriberType;
	}

	public void setSubscriberType(Short subscriberType) {
		this.subscriberType = subscriberType;
	}

	public Date getJoinDate() {
		return this.joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Integer getPrimaryLocation() {
		return this.primaryLocation;
	}

	public void setPrimaryLocation(Integer primaryLocation) {
		this.primaryLocation = primaryLocation;
	}

	public Integer getSecondaryLocation() {
		return this.secondaryLocation;
	}

	public void setSecondaryLocation(Integer secondaryLocation) {
		this.secondaryLocation = secondaryLocation;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getActivated() {
		return this.activated;
	}

	public void setActivated(Integer activated) {
		this.activated = activated;
	}

	public String getDisplayName(){
		return getName()+" "+ getSurname().substring(0,1)+".";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
