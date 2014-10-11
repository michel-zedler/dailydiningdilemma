package ddd.impl.filter;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;

import ddd.impl.model.UserModel;
import ddd.impl.service.UserService;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {

	@Inject
	private UserService userService;

	@Context
	private ResourceInfo resourceInfo;

	@Override
	public void filter(ContainerRequestContext context) throws IOException {
		String apiKey = context.getHeaders().getFirst("apikey");

		if (Strings.isNullOrEmpty(apiKey)) {
			return;
		}

		UserModel user = userService.findByApiKey(apiKey);

		if (user == null) {
			return;
		}
		
		DddSecurityContext securityContext = new DddSecurityContext(user);

		context.setSecurityContext(securityContext);
	}
	
	class DddSecurityContext implements SecurityContext {

		private final UserModel user;
		private final Set<String> roles;

		public DddSecurityContext(UserModel userModel) {
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
		public Principal getUserPrincipal() {
			return new Principal() {
				@Override
				public String getName() {
					return user.getId().toString();
				}
			};
		}

		@Override
		public boolean isUserInRole(String role) {
			return roles.contains(role);
		}

		@Override
		public boolean isSecure() {
			return true;
		}

		@Override
		public String getAuthenticationScheme() {
			return "apikey";
		}
	}
}
