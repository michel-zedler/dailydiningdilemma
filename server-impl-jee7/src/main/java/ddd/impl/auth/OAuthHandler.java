package ddd.impl.auth;

import ddd.impl.model.UserModel;

public interface OAuthHandler {
	
	UserModel login(String token);

}
