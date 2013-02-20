package com.yaser.social_quick.config;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.support.OAuth1ConnectionFactory;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.connect.web.ConnectSupport;
import org.springframework.social.connect.web.ProviderSignInAttempt;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.view.RedirectView;

public class MyProviderSigninController extends ProviderSignInController {

	private final static Log logger = LogFactory.getLog(ProviderSignInController.class);
	
	private final ConnectionFactoryLocator connectionFactoryLocator;
	
	private final UsersConnectionRepository usersConnectionRepository;
	
	private final SignInAdapter signInAdapter;
	
	private String signInUrl = "/signin";
	
	private String signUpUrl = "/signup";
	
	private String postSignInUrl = "/";
	
	private final ConnectSupport webSupport = new ConnectSupport();

	@Inject
	public MyProviderSigninController(
			ConnectionFactoryLocator connectionFactoryLocator,
			UsersConnectionRepository usersConnectionRepository,
			SignInAdapter signInAdapter) {
		
		super(connectionFactoryLocator, usersConnectionRepository,
				signInAdapter);
		
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.usersConnectionRepository = usersConnectionRepository;
		this.signInAdapter = signInAdapter;
		this.webSupport.setUseAuthenticateUrl(true);
	}



	/**
	 * Sets the URL of the application's sign in page.
	 * Defaults to "/signin".
	 * @param signInUrl the signIn URL
	 */
	public void setSignInUrl(String signInUrl) {
		this.signInUrl = signInUrl;
	}
	
	/**
	 * Sets the URL to redirect the user to if no local user account can be mapped when signing in using a provider.
	 * Defaults to "/signup". 
	 * @param signUpUrl the signUp URL
	 */
	public void setSignUpUrl(String signUpUrl) {
		this.signUpUrl = signUpUrl; 
	}

	/**
 	 * Sets the default URL to redirect the user to after signing in using a provider.
 	 * Defaults to "/".
	 * @param postSignInUrl the postSignIn URL
	 */
	public void setPostSignInUrl(String postSignInUrl) {
		this.postSignInUrl = postSignInUrl;
	}

	/**
	 * Configures the base secure URL for the application this controller is being used in e.g. <code>https://myapp.com</code>. Defaults to null.
	 * If specified, will be used to generate OAuth callback URLs.
	 * If not specified, OAuth callback URLs are generated from web request info.
	 * You may wish to set this property if requests into your application flow through a proxy to your application server.
	 * In this case, the request URI may contain a scheme, host, and/or port value that points to an internal server not appropriate for an external callback URL.
	 * If you have this problem, you can set this property to the base external URL for your application and it will be used to construct the callback URL instead.
	 * @param applicationUrl the application URL value
	 */
	public void setApplicationUrl(String applicationUrl) {
		webSupport.setApplicationUrl(applicationUrl);
	}

	public RedirectView signIn(@PathVariable String providerId, NativeWebRequest request) {
		ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(providerId);
		try {
			return new RedirectView(webSupport.buildOAuthUrl(connectionFactory, request));
		} catch (Exception e) {
			return redirect(URIBuilder.fromUri(signInUrl).queryParam("error", "provider").build().toString());
		}
	}

	public RedirectView oauth1Callback(@PathVariable String providerId, NativeWebRequest request) {
		try {
			OAuth1ConnectionFactory<?> connectionFactory = (OAuth1ConnectionFactory<?>) connectionFactoryLocator.getConnectionFactory(providerId);
			Connection<?> connection = webSupport.completeConnection(connectionFactory, request);
			return handleSignIn(connection, request);
		} catch (Exception e) {
			return redirect(URIBuilder.fromUri(signInUrl).queryParam("error", "provider").build().toString());
		}
	}

	public RedirectView oauth2Callback(@PathVariable String providerId, @RequestParam("code") String code, NativeWebRequest request) {
		try {
			OAuth2ConnectionFactory<?> connectionFactory = (OAuth2ConnectionFactory<?>) connectionFactoryLocator.getConnectionFactory(providerId);
			Connection<?> connection = webSupport.completeConnection(connectionFactory, request);
			return handleSignIn(connection, request);
		} catch (Exception e) {
			logger.warn("Exception while handling OAuth2 callback (" + e.getMessage() + "). Redirecting to " + signInUrl);
			return redirect(URIBuilder.fromUri(signInUrl).queryParam("error", "provider").build().toString());
		}
	}
	
	public RedirectView canceledAuthorizationCallback() {
		return redirect(signInUrl);
	}

	// internal helpers

	private RedirectView handleSignIn(Connection<?> connection, NativeWebRequest request) {
		List<String> userIds = usersConnectionRepository.findUserIdsWithConnection(connection);
		if (userIds.size() == 0) {
			ProviderSignInAttempt signInAttempt = new ProviderSignInAttempt(connection, connectionFactoryLocator, usersConnectionRepository);
			request.setAttribute(ProviderSignInAttempt.SESSION_ATTRIBUTE, signInAttempt, RequestAttributes.SCOPE_SESSION);
			return redirect(signUpUrl);
		} else if (userIds.size() == 1) {
			usersConnectionRepository.createConnectionRepository(userIds.get(0)).updateConnection(connection);
			String originalUrl = signInAdapter.signIn(userIds.get(0), connection, request);
			return originalUrl != null ? redirect(originalUrl) : redirect(postSignInUrl);
		} else {
			return redirect(URIBuilder.fromUri(signInUrl).queryParam("error", "multiple_users").build().toString());
		}
	}

	private RedirectView redirect(String url) {
		return new RedirectView(url, true);
	}

}
