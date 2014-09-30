package ddd.impl.auth;

import ddd.impl.entity.UserEntity;

public interface OAuthHandler {
	
	UserEntity login(String token);

}
