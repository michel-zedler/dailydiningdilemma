package ddd.impl.auth;

import ddd.api.model.DeviceInfo;
import ddd.impl.model.UserModel;

public interface OAuthHandler {
	
	UserModel login(String token, DeviceInfo deviceInfo);

	String getService();
}
