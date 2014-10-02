package ddd.impl.auth;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;

import ddd.impl.entity.UserEntity;
import ddd.impl.facebookclient.FacebookClient;
import ddd.impl.facebookclient.FacebookMeResponse;
import ddd.impl.model.UserModel;
import ddd.impl.service.UserService;

@ApplicationScoped
public class FacebookAuthHandler implements OAuthHandler {

	@Inject
	private Logger logger;

	@Inject
	private UserService userService;

	@Inject
	private FacebookClient facebookClient;

	@Override
	public UserModel login(String token) {
		FacebookMeResponse user = facebookClient.getMe(token);
		String facebookId = user.getId();

		UserModel model = userService.findByFacebookId(facebookId);

		if (model == null) {
			logger.info(
					"No user found with facebook-id {}. Creating new user.",
					facebookId);
			model = new UserModel();
			model.setFacebookId(facebookId);
			model.setDisplayName(user.getName());
		}

		if (model.getApiKey() == null) {
			model.setApiKey(RandomStringUtils.randomAlphanumeric(64));
		}

		userService.save(model);

		return model;
	}

}
