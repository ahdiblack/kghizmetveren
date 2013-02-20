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
package com.yaser.social_quick.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.support.URIBuilder;

import com.yaser.app.SessionUtil;
import com.yaser.data.model.TblSubscriber;
import com.yaser.social_quick.user.SimpleConnectionSignUp;
import com.yaser.social_quick.user.SimpleSignInAdapter;
import com.yaser.social_quick.user.UserInterceptor;

/**
 * Spring Social Configuration.
 */
@Configuration
public class SocialConfig {

	@Inject
	private DataSource dataSource;
	
	@Inject
	private Environment environment;
	
	@Inject
	private SimpleSignInAdapter simpleSignInAdapter;
	
	@Inject
	private SimpleConnectionSignUp simpleConnectionSignUp;
	
	@Inject
	SessionUtil sessionUtil;
	
	@Inject 
	private UsersConnectionRepository usersConnectionRepository;
	
	
	@Bean
	public ProviderSignInController providerSignInController() {
		ProviderSignInController sc = new ProviderSignInController(connectionFactoryLocator(), usersConnectionRepository(),
		        simpleSignInAdapter);
		sc.setPostSignInUrl(URIBuilder.fromUri("/index").build().toString());
		sc.setSignUpUrl(URIBuilder.fromUri("/").build().toString());
	    return sc;
	}
	
	@Bean
	public ConnectionFactoryLocator connectionFactoryLocator() {
	    ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
	    FacebookConnectionFactory fbCon = new FacebookConnectionFactory(environment.getProperty("facebook.clientId"),
		        environment.getProperty("facebook.clientSecret"));
	    fbCon.setScope(environment.getProperty("permissions"));
	    registry.addConnectionFactory(fbCon);
	    return registry;
	}
	
	@Bean
	public UsersConnectionRepository usersConnectionRepository() {
	    JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, 
	        connectionFactoryLocator(), Encryptors.noOpText());
	    repository.setConnectionSignUp(simpleConnectionSignUp);
	    return repository;
	}
	
	@Bean
	public UserInterceptor userInterceptor() {
		return new UserInterceptor(usersConnectionRepository);
	}
	
//	@Bean
//	public UsersConnectionRepository usersConnectionRepository() {
//	    JpaUsersConnectionRepository repository = new JpaUsersConnectionRepository(jpaTemplate, 
//	        connectionFactoryLocator(), Encryptors.noOpText());
//	    repository.setConnectionSignUp(new SimpleConnectionSignUp());
//	    return repository;
//	}

	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
	public ConnectionRepository connectionRepository() {
//	    User user = SecurityContext.getCurrentUser();
		TblSubscriber user = sessionUtil.getUser();
	    return usersConnectionRepository().createConnectionRepository(user.getFacebookId());
	}



	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)	
	public Facebook facebook() {
	    return connectionRepository().getPrimaryConnection(Facebook.class).getApi();
	}

}