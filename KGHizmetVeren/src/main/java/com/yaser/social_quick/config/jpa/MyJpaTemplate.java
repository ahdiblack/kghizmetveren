package com.yaser.social_quick.config.jpa;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class MyJpaTemplate implements JpaTemplate{

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public Set<String> findUsersConnectedTo(String providerId,
			Set<String> providerUserIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RemoteUser> getPrimary(String userId, String providerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRank(String userId, String providerId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<RemoteUser> getAll(String userId,
			MultiValueMap<String, String> providerUsers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RemoteUser> getAll(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RemoteUser> getAll(String userId, String providerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemoteUser get(String userId, String providerId,
			String providerUserId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RemoteUser> get(String providerId, String providerUserId)
			throws IncorrectResultSizeDataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String userId, String providerId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String userId, String providerId, String providerUserId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RemoteUser createRemoteUser(String userId, String providerId,
			String providerUserId, int rank, String displayName,
			String profileUrl, String imageUrl, String accessToken,
			String secret, String refreshToken, Long expireTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemoteUser save(RemoteUser user) {
		// TODO Auto-generated method stub
		return null;
	}

}
