package ddd.impl.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import com.google.common.base.Strings;

import ddd.impl.model.UserModel;
import ddd.impl.security.DddPrincipal;
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
		
		DddPrincipal principle = new DddPrincipal(user);
		DddSecurityContext securityContext = new DddSecurityContext(principle);

		context.setSecurityContext(securityContext);
	}
	
}
