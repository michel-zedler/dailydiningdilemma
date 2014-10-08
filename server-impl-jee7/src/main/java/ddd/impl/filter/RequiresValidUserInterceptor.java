package ddd.impl.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;

import ddd.impl.annotation.RequiresValidUser;
import ddd.impl.service.UserService;

@Provider
@RequiresValidUser
public class RequiresValidUserInterceptor implements ContainerRequestFilter {

	@Inject
	private UserService userService;

	@Override
	public void filter(ContainerRequestContext context) throws IOException {
		String apikey = context.getHeaders().getFirst("apikey");

		if (StringUtils.isBlank(apikey)) {
			abortRequest(context, "no api-key provided");
			return;
		}
		
		boolean keyValid = userService.isApiKeyValid(apikey);
		
		if (keyValid == false) {
			abortRequest(context, "api-key invalid");
			return;
		}

	}

	private void abortRequest(ContainerRequestContext context, String message) {
		context.abortWith(Response.status(Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN_TYPE).entity(message).build());
	}

}
