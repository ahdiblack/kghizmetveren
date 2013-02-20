package com.yaser.app;

import com.yaser.social_quick.user.User;

public class SecurityUser {

	public static User currentUser;

	public static User getCurrentUser() {
		User user = currentUser;
		if (user == null) {
			throw new IllegalStateException("No user is currently signed in");
		}
		return user;
	}

	public static boolean userSignedIn() {
		return currentUser != null;
	}

	public static void remove() {
		currentUser=null;
	}
}
