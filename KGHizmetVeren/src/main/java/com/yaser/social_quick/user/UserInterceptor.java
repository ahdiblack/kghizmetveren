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

import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

import com.yaser.app.SecurityUser;
import com.yaser.app.SessionUtil;
import com.yaser.data.dao.TblSubscriberDAO;
import com.yaser.data.model.TblSubscriber;

/**
 * Before a request is handled:
 * 1. sets the current User in the {@link SecurityUser} from a cookie, if present and the user is still connected to Facebook.
 * 2. requires that the user sign-in if he or she hasn't already.
 */
public final class UserInterceptor extends HandlerInterceptorAdapter {

	private final UsersConnectionRepository connectionRepository;
	
	private final UserCookieGenerator userCookieGenerator = new UserCookieGenerator();
	
	@Inject
	SessionUtil sessionUtil;
	
	@Inject
	TblSubscriberDAO tblSubscriberDAO;

	public UserInterceptor(UsersConnectionRepository connectionRepository) {
		this.connectionRepository = connectionRepository;
	}
	
	
	public UserInterceptor() {
		connectionRepository = null;
	}
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		rememberUser(request, response);
		handleSignOut(request, response);
		
		if (sessionUtil.userSignedIn() || requestForSignIn(request)) {
			return true;
		} else {
			return requireSignIn(request, response);
		}
	}
	
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//		SecurityContext.remove();
	}

	// internal helpers

	private void rememberUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = userCookieGenerator.readCookieValue(request);
		if (userId == null) {
			return;
		}
		if (!userNotFound(userId)) {
			userCookieGenerator.removeCookie(response);
			return;
		}
		if(!sessionUtil.userSignedIn()){
			TblSubscriber user = tblSubscriberDAO.findByFBId(userId);
			if(user != null){
				sessionUtil.setUser(user);
				new RedirectView("/index", true);
			} else {
				new RedirectView("/signin", true);
			}
		}
	}

	private void handleSignOut(HttpServletRequest request, HttpServletResponse response) {
		if (sessionUtil.userSignedIn() && request.getServletPath().startsWith("/signout")) {
//			connectionRepository.createConnectionRepository(SecurityContext.getCurrentUser().getId()).removeConnections("facebook");
			userCookieGenerator.removeCookie(response);
			sessionUtil.remove();
//			SecurityContext.remove();			
		}
	}
		
	private boolean requestForSignIn(HttpServletRequest request) {
		return request.getServletPath().startsWith("/signin");
	}
	
	private boolean requireSignIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		new RedirectView("/signin", true).render(null, request, response);
		return false;
	}

	private boolean userNotFound(String userId) {
		// doesn't bother checking a local user database: simply checks if the userId is connected to Facebook
		return connectionRepository.createConnectionRepository(userId).findPrimaryConnection(Facebook.class) != null;
	}
	
}