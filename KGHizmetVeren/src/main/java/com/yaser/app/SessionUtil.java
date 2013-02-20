package com.yaser.app;

import org.springframework.stereotype.Component;

import com.yaser.data.model.TblSubscriber;

@Component
public class SessionUtil {

	private TblSubscriber user;
	
	
	public void setUser(TblSubscriber user) {
		this.user = user;
	}
	
	public TblSubscriber getUser() {
		return user;
	}
	
	public boolean userSignedIn() {
		return this.user != null;
	}
	
	public void remove() {
		this.user = null;
	}
}
