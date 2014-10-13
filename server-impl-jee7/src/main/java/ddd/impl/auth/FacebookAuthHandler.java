package ddd.impl.auth;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ddd.api.model.DeviceInfo;
import ddd.impl.facebookclient.FacebookClient;
import ddd.impl.facebookclient.FacebookMeResponse;
import ddd.impl.model.UserModel;
import ddd.impl.service.UserService;

@ApplicationScoped
public class FacebookAuthHandler implements OAuthHandler {

	@Inject
	private UserService userService;

	@Inject
	private FacebookClient facebookClient;

	@Override
	public UserModel login(String token, DeviceInfo deviceInfo) {
		FacebookMeResponse meResponse = facebookClient.getMe(token);

		OAuthProfile profile = map(meResponse);

		UserModel model = userService.loginUser(profile, deviceInfo);

		return model;
	}

	private OAuthProfile map(FacebookMeResponse meResponse) {
		OAuthProfile profile = new OAuthProfile();
		
		profile.setServiceId(meResponse.getId());
		profile.setServiceName(getService());
		
		profile.setDisplayName(meResponse.getName());
		profile.setFirstName(meResponse.getFirstName());
		profile.setLastName(meResponse.getLastName());
		profile.setEmail(meResponse.getEmail());
		
		return profile;
	}

	@Override
	public String getService() {
		return "facebook";
	}

}
