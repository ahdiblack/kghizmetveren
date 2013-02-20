/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yaser.social_quick.user;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import com.yaser.app.SecurityContext;
import com.yaser.app.SecurityUser;
import com.yaser.app.SessionUtil;
import com.yaser.data.dao.TblSubscriberDAO;
import com.yaser.data.model.TblSubscriber;

/**
 * Signs the user in by setting the currentUser property on the {@link SecurityUser}.
 * Remembers the sign-in after the current request completes by storing the user's id in a cookie.
 * This is cookie is read in {@link UserInterceptor#preHandle(HttpServletRequest, HttpServletResponse, Object)} on subsequent requests.
 * @see UserInterceptor
 */
@Component
public class SimpleSignInAdapter implements SignInAdapter {

	private final UserCookieGenerator userCookieGenerator = new UserCookieGenerator();
	
	@Inject
	TblSubscriberDAO tblSubscriberDAO;
	
	@Inject
	SessionUtil sessionUtil;
	
	public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
		SecurityContext.setCurrentUser(new User(userId));
		TblSubscriber user = tblSubscriberDAO.findByFBId(connection.getKey().getProviderUserId());
		if (user == null) {
			saveUser(connection);
		}
		sessionUtil.setUser(user);
		userCookieGenerator.addCookie(user.getFacebookId(), request.getNativeResponse(HttpServletResponse.class));
		return null;
	}

	private void saveUser(Connection<?> connection) {
		tblSubscriberDAO.save(createUser(connection));
	}
	
	private TblSubscriber createUser(Connection<?> connection){
		UserProfile profile = connection.fetchUserProfile();
		
		TblSubscriber user = new TblSubscriber();
		user.setFacebookId(connection.getKey().getProviderUserId());
		
		user.setImageUrl(connection.getImageUrl());
		user.setName(profile.getFirstName());
		user.setSurname(profile.getLastName());
		user.setEmail(profile.getEmail());
		return user;
	}

}