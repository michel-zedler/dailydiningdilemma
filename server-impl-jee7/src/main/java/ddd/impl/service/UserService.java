package ddd.impl.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;

import ddd.impl.auth.OAuthProfile;
import ddd.impl.dao.UserDao;
import ddd.impl.entity.UserEntity;
import ddd.impl.entity.UserOAuthIdMapping;
import ddd.impl.model.UserModel;

@Stateless
public class UserService {

	@Inject
	private UserDao userDao;

	public UserModel loginUser(OAuthProfile profile) {
		UserEntity user = userDao.findByServiceNameAndId(
				profile.getServiceName(), profile.getServiceId());

		if (user == null) {
			user = new UserEntity();
			user.setApiKey(createApiKey());
			user.setDisplayName(profile.getDisplayName());

			userDao.persist(user);

			addServiceOAuthBindung(user, profile.getServiceName(), profile.getServiceId());
		}

		if (user.getApiKey() == null) {
			user.setApiKey(createApiKey());
		}
		
		return toModel(user);
	}

	public void renewApiKey(UserModel userModel) {
		UserEntity userEntity = userDao.findById(userModel.getId());
		String apiKey = createApiKey();
		userEntity.setApiKey(apiKey);
		userModel.setApiKey(apiKey);
	}

	public String createApiKey() {
		return RandomStringUtils.randomAlphanumeric(128);
	}

	public void update(UserModel model) {
		UserEntity entity = userDao.findById(model.getId());
		updateEntity(entity, model);
	}

	public void addServiceOAuthBindung(UserModel userModel, String serviceName,
			String serviceId) {
		UserEntity entity = userDao.findById(userModel.getId());
		addServiceOAuthBindung(entity, serviceName, serviceId);
	}
	
	public void removeApiKey(String apiKey) {
		UserEntity entity = userDao.findByApiKey(apiKey);
		entity.setApiKey(null);
	}

	protected void addServiceOAuthBindung(UserEntity entity,
			String serviceName, String serviceId) {
		UserOAuthIdMapping mapping = new UserOAuthIdMapping();

		mapping.setUser(entity);
		mapping.setServiceName(serviceName);
		mapping.setSeriveId(serviceId);

		userDao.persist(mapping);
	}

	private void updateEntity(UserEntity entity, UserModel model) {
		entity.setApiKey(model.getApiKey());
		entity.setDisplayName(model.getDisplayName());
	}

	private UserModel toModel(UserEntity entity) {
		UserModel model = new UserModel();

		model.setId(entity.getId());
		model.setApiKey(entity.getApiKey());
		model.setDisplayName(entity.getDisplayName());

		return model;
	}

	public boolean isApiKeyValid(String apiKey) {
		UserEntity e = userDao.findByApiKey(apiKey);
		
		if (e == null) {
			return false;
		}
		
		return true;
	}
}
