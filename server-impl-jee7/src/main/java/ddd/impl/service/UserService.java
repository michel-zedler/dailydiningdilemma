package ddd.impl.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ddd.impl.dao.UserDao;
import ddd.impl.entity.UserEntity;

@Stateless
public class UserService {

	@Inject
	private UserDao userDao;

	public UserEntity findByFacebookId(String facebookId) {
		return userDao.findByFacebookId(facebookId);
	}

	public void save(UserEntity user) {
		if (user.getId() == null) {
			userDao.persist(user);
		} else {
			UserEntity original = userDao.findById(user.getId());

			original.setApiKey(user.getApiKey());
			original.setDisplayName(user.getDisplayName());
			original.setFacebookId(user.getFacebookId());
		}
	}
}
