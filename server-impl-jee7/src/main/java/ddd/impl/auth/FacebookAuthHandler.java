package ddd.impl.auth;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

import ddd.impl.entity.UserEntity;
import ddd.impl.service.UserService;

@ApplicationScoped
public class FacebookAuthHandler implements OAuthHandler {

	@Inject
	private Logger logger;

	@Inject
	private UserService userService;

	@Override
	public UserEntity login(String token) {
		FacebookClient facebookClient = new DefaultFacebookClient(token);

		User user = facebookClient.fetchObject("me", User.class);

		String facebookId = user.getId();

		UserEntity userEntity = userService.findByFacebookId(facebookId);

		if (userEntity == null) {
			logger.info(
					"No user found with facebook-id {}. Creating new user.",
					facebookId);
			userEntity = new UserEntity();
			userEntity.setFacebookId(facebookId);
			userEntity.setDisplayName(user.getName());
		}

		if (userEntity.getApiKey() == null) {
			userEntity.setApiKey(RandomStringUtils.randomAlphanumeric(64));
		}

		userService.save(userEntity);

		return userEntity;
	}

}
