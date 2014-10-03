package ddd.impl.rest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;

import ddd.api.model.LoginResult;
import ddd.api.rest.Login;
import ddd.impl.auth.OAuthHandler;
import ddd.impl.model.UserModel;

@Path("/")
public class LoginServiceRest implements Login {

	@Inject
	Logger logger;

	private Map<String, OAuthHandler> handlerRegister = new HashMap<String, OAuthHandler>();

	public LoginServiceRest() {
	}

	@Inject
	public LoginServiceRest(@Any Instance<OAuthHandler> oAuthHandlers) {
		for (Iterator<OAuthHandler> i = oAuthHandlers.iterator(); i.hasNext();) {
			OAuthHandler handler = i.next();
			handlerRegister.put(handler.getService(), handler);
		}
	}

	@Override
	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Path("/login")
	public Response login(@FormParam("service") String service,
			@FormParam("token") String token) {
		if (token == null || token.equals("")) {
			return Response.serverError().build();
		}

		logger.debug(token);
		
		OAuthHandler handler = handlerRegister.get(service);
		
		if (handler == null) {
			String message = String.format("Service %s not yet supported for oAuthLogin.", service);
			logger.info(message);
			return buildLoginFailure(message);
		}
		
		try {
			LoginResult result = new LoginResult();
			UserModel entity = handler.login(token);
			result.setApiKey(entity.getApiKey());
			result.setDisplayName(entity.getDisplayName());
			result.setSuccess(true);
			return buildResponse(result);
		} catch (Exception e) {
			String message = "Error. See Server logs.";
			return buildLoginFailure(message);
		}
	}

	private Response buildLoginFailure(String errorMessage) {
		LoginResult result = new LoginResult();
		
		result.setErrorMessage(errorMessage);
		result.setSuccess(false);
		
		return buildResponse(result);
	}

	private Response buildResponse(LoginResult result) {
		return Response.ok(result).type(MediaType.APPLICATION_JSON_TYPE)
				.build();
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

}
