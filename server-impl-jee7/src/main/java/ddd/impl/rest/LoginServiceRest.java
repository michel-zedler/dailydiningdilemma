package ddd.impl.rest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;

import ddd.api.request.LoginRequest;
import ddd.api.response.LoginResponse;
import ddd.api.response.LogoutRequest;
import ddd.impl.auth.OAuthHandler;
import ddd.impl.model.UserModel;
import ddd.impl.service.UserService;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginServiceRest {

	@Inject
	Logger logger;

	@Inject
	Validator validator;

	@Inject
	ValidationHelper validationHelper;
	
	@Inject
	UserService userService;

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

	@POST
	@Path("/login")
	public Response login(LoginRequest loginRequest) {
		String token = loginRequest.getToken();
		String service = loginRequest.getService();

		Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
		if (violations.isEmpty() == false) {
			return validationHelper.buildValidationFailureResponse(violations);
		}

		OAuthHandler handler = handlerRegister.get(service);

		if (handler == null) {
			String message = String.format("Service %s not yet supported for oAuthLogin.", service);
			logger.info(message);
			return buildLoginFailure(message);
		}

		try {
			LoginResponse result = new LoginResponse();
			UserModel entity = handler.login(token, loginRequest.getDeviceInfo());
			result.setApiKey(entity.getApiKey());
			result.setDisplayName(entity.getDisplayName());
			result.setSuccess(true);
			return buildResponse(result);
		} catch (Exception e) {
			String message = "Error. See Server logs.";
			logger.warn("Error", e);
			return buildLoginFailure(message);
		}
	}

	private Response buildLoginFailure(String errorMessage) {
		LoginResponse result = new LoginResponse();

		result.setErrorMessage(errorMessage);
		result.setSuccess(false);

		return buildResponse(result);
	}

	private Response buildResponse(LoginResponse result) {
		return Response.ok(result).type(MediaType.APPLICATION_JSON_TYPE).build();
	}


	@POST
	@Path("/logout")
	public Response logout(LogoutRequest logoutRequest) {
		try {
			userService.removeApiKey(logoutRequest.getApiKey());
		} catch(Exception e) {
			logger.warn("API Key not found.");
		}
		return Response.ok().build();
	}
}
