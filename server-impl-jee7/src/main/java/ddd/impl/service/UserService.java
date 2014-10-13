package ddd.impl.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;

import ddd.api.model.DeviceInfo;
import ddd.impl.auth.OAuthProfile;
import ddd.impl.dao.UserDao;
import ddd.impl.entity.DeviceEntity;
import ddd.impl.entity.UserEntity;
import ddd.impl.entity.UserOAuthIdMapping;
import ddd.impl.model.UserModel;

@Stateless
public class UserService {

	@Inject
	private UserDao userDao;

	public UserModel loginUser(OAuthProfile profile, DeviceInfo deviceInfo) {
		UserEntity user = userDao.findByServiceNameAndId(
				profile.getServiceName(), profile.getServiceId());

		if (user == null) {
			user = new UserEntity();
			user.setDisplayName(profile.getDisplayName());
			userDao.persist(user);

			addServiceOAuthBindung(user, profile.getServiceName(), profile.getServiceId());
		}

		userDao.deleteExistingDeviceEntity(user, deviceInfo.getModel(), deviceInfo.getPlatform(), deviceInfo.getUuid());

		DeviceEntity deviceEntity = new DeviceEntity();
		deviceEntity.setApiKey(createApiKey());
		deviceEntity.setUser(user);
		deviceEntity.setUuid(deviceInfo.getUuid());
		deviceEntity.setPlatform(deviceInfo.getPlatform());
		deviceEntity.setModel(deviceInfo.getModel());
		
		userDao.persist(deviceEntity);

		return toModel(user, deviceEntity);
	}

	public String createApiKey() {
		return RandomStringUtils.randomAlphanumeric(128);
	}

	public void addServiceOAuthBindung(UserModel userModel, String serviceName,
			String serviceId) {
		UserEntity entity = userDao.findById(userModel.getId());
		addServiceOAuthBindung(entity, serviceName, serviceId);
	}
	
	public void removeApiKey(String apiKey) {
		userDao.deleteApiKey(apiKey);
	}

	protected void addServiceOAuthBindung(UserEntity entity,
			String serviceName, String serviceId) {
		UserOAuthIdMapping mapping = new UserOAuthIdMapping();

		mapping.setUser(entity);
		mapping.setServiceName(serviceName);
		mapping.setSeriveId(serviceId);

		userDao.persist(mapping);
	}

	private UserModel toModel(UserEntity entity, DeviceEntity deviceEntity) {
		UserModel model = new UserModel();

		model.setId(entity.getId());
		model.setApiKey(deviceEntity.getApiKey());
		model.setDisplayName(entity.getDisplayName());
		model.setRoles(entity.getRoles());
		
		return model;
	}

	public UserModel findByApiKey(String apiKey) {
		UserEntity entity = userDao.findByApiKey(apiKey);
		
		if (entity == null) {
			return null;
		}
		
		return toModel(entity, null);
	}
	
	public boolean isApiKeyValid(String apiKey) {
		UserEntity e = userDao.findByApiKey(apiKey);
		
		if (e == null) {
			return false;
		}
		
		return true;
	}
}
