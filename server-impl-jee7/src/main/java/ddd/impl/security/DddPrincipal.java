package ddd.impl.security;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;

import ddd.impl.model.UserModel;

public class DddPrincipal implements Principal {

	private final UserModel user;
	private final Set<String> roles;

	public DddPrincipal(UserModel userModel) {
		this.user = userModel;
		this.roles = parseRoles(userModel);
	}
	
	private Set<String> parseRoles(UserModel user) {
		Set<String> result = new HashSet<String>();
		result.add("user");

		if (Strings.isNullOrEmpty(user.getRoles()) == false) {
			String[] roles = StringUtils.split(user.getRoles(), ",");
			result.addAll(Arrays.asList(roles));
		}
		
		return result;
	}
	
	@Override
	public String getName() {
		return user.getDisplayName();
	}

	public Set<String> getRoles() {
		return roles;
	}
	
	public UserModel getUser() {
		return user;
	}

	public String getApiKey() {
		return user.getApiKey();
	}
	
}
