package ddd.impl.auth;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;

import ddd.impl.googleclient.GoogleClient;
import ddd.impl.googleclient.GoogleMeResponse;
import ddd.impl.model.UserModel;
import ddd.impl.service.UserService;

@ApplicationScoped
public class GoogleAuthHandler implements OAuthHandler {

	@Inject
	private Logger logger;

	@Inject
	private UserService userService;
	
	@Inject
	private GoogleClient googleClient;

	@Override
	public UserModel login(String token) {
		GoogleMeResponse user = googleClient.getMe(token); 
		String oauthId = user.getId();

		UserModel model = userService.findByGoogleId(oauthId);

		if (model == null) {
			logger.info(
					"No user found with facebook-id {}. Creating new user.",
					oauthId);
			model = new UserModel();
			model.setGoogleId(oauthId);
			model.setDisplayName(user.getName());
		}

		if (model.getApiKey() == null) {
			model.setApiKey(RandomStringUtils.randomAlphanumeric(64));
		}

		userService.save(model);

		return model;
	}

}
