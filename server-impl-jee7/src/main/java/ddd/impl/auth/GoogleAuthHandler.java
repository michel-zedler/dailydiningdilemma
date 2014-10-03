package ddd.impl.auth;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ddd.impl.googleclient.GoogleClient;
import ddd.impl.googleclient.GoogleMeResponse;
import ddd.impl.model.UserModel;
import ddd.impl.service.UserService;

@ApplicationScoped
public class GoogleAuthHandler implements OAuthHandler {

	@Inject
	private UserService userService;

	@Inject
	private GoogleClient googleClient;

	@Override
	public UserModel login(String token) {
		GoogleMeResponse meResponse = googleClient.getMe(token);
		
		OAuthProfile profile = map(meResponse);
		
		UserModel model = userService.loginUser(profile);
		
		return model;
	}

	private OAuthProfile map(GoogleMeResponse meResponse) {
		OAuthProfile profile = new OAuthProfile();
		
		profile.setServiceId(meResponse.getId());
		profile.setServiceName(getService());
		
		profile.setDisplayName(meResponse.getName());
		profile.setFirstName(meResponse.getGivenName());
		profile.setLastName(meResponse.getFamilyName());
		profile.setImage(meResponse.getPictureUrl());
		profile.setUrl(meResponse.getLink());
		
		return profile;
	}

	@Override
	public String getService() {
		return "google";
	}

}
