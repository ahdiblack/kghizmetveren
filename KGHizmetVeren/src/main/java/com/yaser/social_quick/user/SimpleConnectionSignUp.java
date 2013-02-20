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

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.yaser.data.dao.TblSubscriberDAO;
import com.yaser.data.model.TblSubscriber;

/**
 * Simple little {@link ConnectionSignUp} command that allocates new userIds in memory.
 * Doesn't bother storing a user record in any local database, since this quickstart just stores the user id in a cookie.
 */
@Component
@Service
public final class SimpleConnectionSignUp implements ConnectionSignUp {

	
	@Inject
	TblSubscriberDAO tblSubscriberDAO;
	
	public String execute(Connection<?> connection) {
		TblSubscriber user = tblSubscriberDAO.findByFBId(connection.getKey().getProviderUserId());
		if ( user == null) {
			saveUser(connection);
		}
		return connection.getKey().getProviderUserId();
	}
	
	private void saveUser(Connection<?> connection) {
		UserProfile profile = connection.fetchUserProfile();
		
		TblSubscriber user = new TblSubscriber();
		user.setFacebookId(connection.getKey().getProviderUserId());
		
		user.setImageUrl(connection.getImageUrl());
		user.setName(profile.getFirstName());
		user.setSurname(profile.getLastName());
		user.setEmail(profile.getEmail());
		
		tblSubscriberDAO.save(user);
	}

}
