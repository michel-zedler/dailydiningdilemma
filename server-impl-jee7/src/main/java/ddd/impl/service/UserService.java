package ddd.impl.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ddd.impl.dao.UserDao;
import ddd.impl.entity.UserEntity;
import ddd.impl.model.UserModel;

@Stateless
public class UserService {

	@Inject
	private UserDao userDao;

	public UserModel findByFacebookId(String facebookId) {
		UserEntity entity = userDao.findByFacebookId(facebookId);
		return toModel(entity);
	}

	public void save(UserModel model) {
		if (model.getId() == null) {
			UserEntity entity = createEntity(model);
			userDao.persist(entity);
		} else {
			UserEntity entity = userDao.findById(model.getId());
			updateEntity(entity, model);
		}
	}

	public UserModel findByGoogleId(String oauthId) {
		UserEntity entity = userDao.findByGoogleId(oauthId);
		return toModel(entity);
	}

	private UserEntity createEntity(UserModel model) {
		UserEntity entity = new UserEntity();
		updateEntity(entity, model);
		return entity;
	}

	public void updateEntity(UserEntity entity, UserModel model) {
		entity.setApiKey(model.getApiKey());
		entity.setDisplayName(model.getDisplayName());
		entity.setFacebookId(model.getFacebookId());
		entity.setGoogleId(model.getGoogleId());
	}

	public UserModel toModel(UserEntity entity) {
		UserModel model = new UserModel();

		model.setId(entity.getId());
		model.setApiKey(entity.getApiKey());
		model.setDisplayName(entity.getDisplayName());

		return model;
	}
}
